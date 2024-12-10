package com.javaproject.customer.bff.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import com.javaproject.customer.bff.dto.UserDTO;
import com.javaproject.customer.bff.exception.UserException;
import com.javaproject.customer.bff.security.HttpClientAuth;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(DefaultService.class);
	private final HttpClientAuth httpClient;
	
	@Value("${http.service-url.user}")
	public String userUrl;

	public UserService(HttpClientAuth httpClient) {
		this.httpClient = httpClient;
	}

	public Mono<List<UserDTO>> getUsers(){
		log.info("init: getUsers");
		log.info("request: {}", userUrl);
		return httpClient.get(userUrl)
				.onStatus(HttpStatusCode:: isError, response -> Mono.error(new UserException("Error getting users")))
				.bodyToMono(new ParameterizedTypeReference<>() {
				});
	}

}
