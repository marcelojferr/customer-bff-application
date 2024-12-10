package com.javaproject.customer.bff.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import com.javaproject.customer.bff.dto.CustomerDTO;
import com.javaproject.customer.bff.exception.UserException;
import com.javaproject.customer.bff.security.HttpClientAuth;

import reactor.core.publisher.Mono;

@Service
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(DefaultService.class);
	private final HttpClientAuth httpClient;
	
	@Value("${http.service-url.customer}")
	public String customerUrl;

	public CustomerService(HttpClientAuth httpClient) {
		this.httpClient = httpClient;
	}

	public Mono<List<CustomerDTO>> getCustomers(){
		log.info("init: getCustomers");
		log.info("request: {}", customerUrl);
		return httpClient.get(customerUrl)
				.onStatus(HttpStatusCode:: isError, response -> Mono.error(new UserException("Error getting customers")))
				.bodyToMono(new ParameterizedTypeReference<>() {
				});
	}

}
