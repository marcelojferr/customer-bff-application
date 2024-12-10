package com.javaproject.customer.bff.controller.router;

import static com.javaproject.customer.bff.configs.ComponentConstants.APP_CUSTOMER;
import static com.javaproject.customer.bff.configs.ComponentConstants.APP_USER;
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
import com.javaproject.customer.bff.dto.ComponentsDTO;
import com.javaproject.customer.bff.service.DefaultService;
import com.javaproject.customer.bff.utils.ServerRequestUtils;
import reactor.core.publisher.Mono;

@Component
public class DefaultRoute {

	private static final Logger log = LoggerFactory.getLogger(DefaultRoute.class);
	
	DefaultService defaultService;
	
	public DefaultRoute(DefaultService defaultService) {
		this.defaultService = defaultService;
	}
	
	public Mono<ServerResponse> router(ServerRequest request){
		var components = new ComponentsDTO();
		
		List<Mono<?>> appMonoData = ServerRequestUtils.getListOfComponentNamesFromQueryParam(request)
			    .stream()
			    .map(componentName ->
			        switch (componentName) {
			            case APP_USER -> defaultService.getUsers()
			                .doOnSuccess(components::setUsers)
			                .doOnError(throwable -> {
			                    log.error("Error getting user information: {}", throwable.getMessage());
			                    components.setErrors(APP_USER, throwable.getMessage());
			                });
			            case APP_CUSTOMER -> defaultService.getCustomers()
			                .doOnSuccess(components::setCustomers)
			                .doOnError(throwable -> {
			                    log.error("Error getting customer information: {}", throwable.getMessage());
			                    components.setErrors(APP_CUSTOMER, throwable.getMessage());
			                });
			            default -> handleDefault(componentName); // Ensure this returns a Mono<?> type
			        })
			    .collect(Collectors.toList());

		return Mono.zip(appMonoData, data -> data)
				.flatMap(tuples -> ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(components), ComponentsDTO.class))
				.onErrorResume(throwable -> badRequest()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(components), ComponentsDTO.class));	
	}
		
	private Mono<Object> handleDefault(String componentName) {
		log.warn("Ignoring invalid component name: {}", componentName);
		return Mono.error(new IllegalArgumentException("Invalid component name: " + componentName));
	}
}
