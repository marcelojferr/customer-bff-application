package com.javaproject.customer.bff.controller.router;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static com.javaproject.customer.bff.configs.ComponentConstants.APP_USER;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.javaproject.customer.bff.dto.ComponentsUserDTO;
import com.javaproject.customer.bff.service.UserService;
import com.javaproject.customer.bff.utils.ServerRequestUtils;
import reactor.core.publisher.Mono;

@Component
public class UserRoute {
private static final Logger log = LoggerFactory.getLogger(UserRoute.class);
	
	UserService userService;
	
	public UserRoute(UserService userService) {
		this.userService = userService;
	}
	
	public Mono<ServerResponse> router(ServerRequest request){
		var userComponent = new ComponentsUserDTO();
		
		List<Mono<?>> appMonoData = ServerRequestUtils.getListOfComponentNamesFromQueryParam(request)
			    .stream()
			    .map(componentName ->
			        switch (componentName) {
			            case APP_USER -> userService.getUsers()
			                .doOnSuccess(userComponent::setUsers)
			                .doOnError(throwable -> {
			                    log.error("Error getting user information: {}", throwable.getMessage());
			                    userComponent.setErrors(APP_USER, throwable.getMessage());
			                });
			            default -> handleDefault(componentName); // Ensure this returns a Mono<?> type
			        })
			    .collect(Collectors.toList());

		return Mono.zip(appMonoData, data -> data)
				.flatMap(tuples -> ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(userComponent), ComponentsUserDTO.class))
				.onErrorResume(throwable -> badRequest()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(userComponent), ComponentsUserDTO.class));	
	}
		
	private Mono<Object> handleDefault(String componentName) {
		log.warn("Ignoring invalid component name: {}", componentName);
		return Mono.error(new IllegalArgumentException("Invalid component name: " + componentName));
	}

}
