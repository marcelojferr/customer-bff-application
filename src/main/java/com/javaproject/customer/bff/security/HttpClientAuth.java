package com.javaproject.customer.bff.security;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserter;
import com.javaproject.customer.bff.security.job.JwtTokenRefresh;

@Component
public class HttpClientAuth {

	private final WebClient webClient;
	private final JwtTokenRefresh tokenService;
	
	public HttpClientAuth(WebClient webClient, JwtTokenRefresh tokenService) {
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
}
