package com.javaproject.customer.bff.controller.router;

import static com.javaproject.customer.bff.controller.ConnectorRouter.CONNECTOR_REQUEST_MAPPING;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.javaproject.customer.bff.controller.ConnectorRouter;
import com.javaproject.customer.bff.dto.CustomerDTO;
import com.javaproject.customer.bff.dto.UserDTO;
import com.javaproject.customer.bff.service.CustomerService;
import com.javaproject.customer.bff.service.DefaultService;
import com.javaproject.customer.bff.service.UserService;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ConnectorTest {
	
	@Mock
	private DefaultService defaultService;

	@Mock
	private CustomerService customerService;

	@Mock
	private UserService userService;
	
	@InjectMocks
	private ConnectorRouter connectorRoute;
	
	private WebTestClient client;

	@BeforeEach
	void setUp() {
		final DefaultRoute defaultRoute = new DefaultRoute(defaultService);
		final CustomerRoute customerRoute = new CustomerRoute(customerService);
		final UserRoute userRoute = new UserRoute(userService);
		
		client = WebTestClient
				.bindToRouterFunction(connectorRoute.connectorRoute(defaultRoute, customerRoute, userRoute))
				.build();
	}
	
	@Test
	@DisplayName("Should return components")
	void getComponentData() {
		Mono<List<CustomerDTO>> customer = Mono.just(List.of(new CustomerDTO()));
		Mockito.when(defaultService.getCustomers()).thenReturn(customer);
		
		Mono<List<UserDTO>> user = Mono.just(List.of(new UserDTO()));
		Mockito.when(defaultService.getUsers()).thenReturn(user);
		
		client.get()
			.uri(CONNECTOR_REQUEST_MAPPING)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.app-customer").hasJsonPath()
			.jsonPath("$.app-user").hasJsonPath();
	}
}

