package com.javaproject.customer.bff.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import com.javaproject.customer.bff.security.service.JwtToken;

import org.springframework.http.client.reactive.ClientHttpRequest;

@Component
public class HttpClientAuth {

	@Value("${http.client-id}")
	private String clientId;

	@Value("${http.secret-id}")
	private String secretId;
	
	private final WebClient webClient;
	private final JwtToken tokenService;
	
	public HttpClientAuth(WebClient webClient, JwtToken tokenService) {
		this.webClient = webClient;
		this.tokenService = tokenService;
	}
	
	public WebClient.ResponseSpec get(String uri){
		return webClient.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.getToken()))
				.retrieve();
	}

	public WebClient.ResponseSpec post(String uri, BodyInserter<?, ? super ClientHttpRequest> body){
		return webClient.post()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.getToken()))
				.body(body)
				.retrieve();
	}

	public WebClient.ResponseSpec put(String uri, BodyInserter<?, ? super ClientHttpRequest> body){
		return webClient.put()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.getToken()))
				.body(body)
				.retrieve();
	}

	
	public WebClient.ResponseSpec delete(String uri){
		return webClient.delete()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.getToken()))
				.retrieve();
	}
	
	public HttpEntity<Object> getRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + tokenService.getJwtToken(clientId, secretId));
		return new HttpEntity<>(headers);
	}

}
