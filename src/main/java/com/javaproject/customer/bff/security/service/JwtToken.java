package com.javaproject.customer.bff.security.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.javaproject.customer.bff.security.dto.JwtTokenRequest;
import com.javaproject.customer.bff.security.dto.JwtTokenResponse;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

@Component
@EnableScheduling
public class JwtToken {

	private static final Logger log = LoggerFactory.getLogger(JwtToken.class);
	private final ParameterizedTypeReference<JwtTokenResponse> typeReference = new ParameterizedTypeReference<>() {};
	private final RestTemplate restTemplate;

	@Value("${http.security.client-id}")
	private String clientId;

	@Value("${http.security.secret-id}")
	private String secretId;
	
	@Value("${http.service-url.token}")
	public String tokenUrl;
	
	public JwtToken(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRate = 12000000)
	void refreshToken()throws MalformedURLException{
		log.info("Refreshing token");
	}
	
	public String getToken() {	
		ResponseEntity<JwtTokenResponse> response = restTemplate
				.exchange(tokenUrl, 
						HttpMethod.POST,
						getTokentRequest(), 
						typeReference);
		
		return response.getBody().getAccessToken();
	}

	public HttpEntity<Object> getTokentRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(clientId, secretId);
		
		JwtTokenRequest request = new JwtTokenRequest(clientId, secretId, "client_credentials");

		return new HttpEntity<>(request, headers);
	}
		
	public String getJwtToken(String clientId, String secretId) {
		long iat = System.currentTimeMillis() / 1000L;
		long exp = iat + 10 * 60;
		
		try {
			return JWT.create()
					.withIssuer(secretId)
					.withIssuedAt(new Date(iat * 1000L))
					.withExpiresAt(new Date(exp * 1000L))
					.sign(Algorithm.RSA256(null, buildSignature(secretId.getBytes())));

		} catch (Exception e) {
			log.error("Something wrong", e.getMessage());
		}
		return null;		
	}

	private static RSAPrivateKey buildSignature(byte[] secretBytes) throws IOException {
		try (InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(secretBytes))) {
			PEMParser pemParser = new PEMParser(reader);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
			Object object = pemParser.readObject();
			KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
			PrivateKey privateKey = kp.getPrivate();
			return (RSAPrivateKey) privateKey;
		}
	}
}

