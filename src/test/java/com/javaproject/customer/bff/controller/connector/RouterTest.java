package com.javaproject.customer.bff.controller.connector;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.javaproject.customer.bff.controller.router.DefaultRoute;
import com.javaproject.customer.bff.dto.CustomerDTO;
import com.javaproject.customer.bff.dto.UserDTO;
import com.javaproject.customer.bff.service.DefaultService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class RouterTest {
	
	@Mock
	private DefaultService defaultService;
	
	@InjectMocks
	private DefaultRoute defaultRoute;

	@Test
	@DisplayName("Should return 200 ok")
	void getConnectorRouter() {
		Mono<List<CustomerDTO>> customer = Mono.just(List.of(new CustomerDTO()));
		Mockito.when(defaultService.getCustomers()).thenReturn(customer);
		
		Mono<List<UserDTO>> user = Mono.just(List.of(new UserDTO()));
		Mockito.when(defaultService.getUsers()).thenReturn(user);
		
		MockServerHttpRequest httpReq = MockServerHttpRequest.get("").build();
		MockServerWebExchange exchange = MockServerWebExchange.builder(httpReq).build();
		MockServerRequest mockServerRequest = MockServerRequest.builder()
				.header("Accept", MediaType.APPLICATION_JSON_VALUE)
				.exchange(exchange)
				.build();
		
		Mono<ServerResponse> responseMono = defaultRoute.router(mockServerRequest);
		
		StepVerifier.create(responseMono)
		.expectNextMatches(response ->
				response.statusCode().equals(HttpStatus.OK) &&
					MediaType.APPLICATION_JSON_VALUE.equals(response.headers().getContentType())
					)
		.verifyComplete();
	
		verify(defaultService, times(1)).getCustomers();
		verify(defaultService, times(1)).getUsers();
	}
}
