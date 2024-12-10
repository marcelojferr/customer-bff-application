package com.javaproject.customer.bff.controller.router;

import static com.javaproject.customer.bff.configs.ComponentConstants.APP_CUSTOMER;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.javaproject.customer.bff.dto.ComponentsCustomerDTO;
import com.javaproject.customer.bff.service.CustomerService;
import com.javaproject.customer.bff.utils.ServerRequestUtils;

import reactor.core.publisher.Mono;

@Component
public class CustomerRoute {
private static final Logger log = LoggerFactory.getLogger(CustomerRoute.class);
	
	CustomerService customerService;
	
	public CustomerRoute(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public Mono<ServerResponse> router(ServerRequest request){
		var customerComponent = new ComponentsCustomerDTO();
		
		List<Mono<?>> appMonoData = ServerRequestUtils.getListOfComponentNamesFromQueryParam(request)
			    .stream()
			    .map(componentName ->
			        switch (componentName) {
			            case APP_CUSTOMER -> customerService.getCustomers()
			                .doOnSuccess(customerComponent::setCustomers)
			                .doOnError(throwable -> {
			                    log.error("Error getting customer information: {}", throwable.getMessage());
			                    customerComponent.setErrors(APP_CUSTOMER, throwable.getMessage());
			                });
			            default -> handleDefault(componentName); // Ensure this returns a Mono<?> type
			        })
			    .collect(Collectors.toList());

		return Mono.zip(appMonoData, data -> data)
				.flatMap(tuples -> ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(customerComponent), ComponentsCustomerDTO.class))
				.onErrorResume(throwable -> badRequest()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(customerComponent), ComponentsCustomerDTO.class));	
	}
		
	private Mono<Object> handleDefault(String componentName) {
		log.warn("Ignoring invalid component name: {}", componentName);
		return Mono.error(new IllegalArgumentException("Invalid component name: " + componentName));
	}

}
