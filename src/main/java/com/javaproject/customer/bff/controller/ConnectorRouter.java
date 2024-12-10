package com.javaproject.customer.bff.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.javaproject.customer.bff.controller.router.CustomerRoute;
import com.javaproject.customer.bff.controller.router.DefaultRoute;
import com.javaproject.customer.bff.controller.router.UserRoute;
import com.javaproject.customer.bff.dto.ComponentsDTO;

import static com.javaproject.customer.bff.configs.RequestConstants.*;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class ConnectorRouter {

	public static final String CONNECTOR_REQUEST_MAPPING = "/connector";
	public static final String CONNECTOR_CUSTOMER_MAPPING = "/connector/customer";
	public static final String CONNECTOR_USER_MAPPING = "/connector/user";
	
	@Bean
	public RouterFunction<ServerResponse> connectorRoute(DefaultRoute getDefault, 
														 CustomerRoute getCustomer, 
														 UserRoute getUser){
		
		return route()
				.GET(CONNECTOR_REQUEST_MAPPING, ACCEPT_JSON, getDefault:: router,  this::getOperations)
				.GET(CONNECTOR_USER_MAPPING, ACCEPT_JSON, getCustomer:: router,  this::getCustomerOperations)
				.GET(CONNECTOR_CUSTOMER_MAPPING, ACCEPT_JSON, getUser:: router,  this::getUserOperations)
				.build();
		}
	
	private void getOperations(org.springdoc.core.fn.builders.operation.Builder ops) {
		ops.operationId(DefaultRoute.class.getSimpleName())
			.summary("Get all routes")
			.beanClass(DefaultRoute.class)
			.beanMethod(CONNECTOR_REQUEST_MAPPING)
			.parameter(parameterBuilder()
					.name(COMPONENT_PARAMETER)
					.description("Set parameter app name")
					.required(false)
					.example("component"))
			.parameter(parameterBuilder()
					.name(PAGE_PARAMETER)
					.description("Set parameter number page")
					.required(false)
					.example("10"))
			.parameter(parameterBuilder()
					.name(SIZE_PARAMETER)
					.description("Set parameter size page")
					.required(false)
					.example("5"))
			.parameter(parameterBuilder()
					.name(SORT_PARAMETER)
					.description("Set parameter sort")
					.required(false)
					.example("name, DESC"))
			.response(buildResponse("200", ComponentsDTO.class))
			.build();
	}

	private void getUserOperations(org.springdoc.core.fn.builders.operation.Builder ops) {
		ops.operationId(UserRoute.class.getSimpleName())
			.summary("Get all routes")
			.beanClass(UserRoute.class)
			.beanMethod(CONNECTOR_REQUEST_MAPPING)
			.parameter(parameterBuilder()
					.name(COMPONENT_PARAMETER)
					.description("Set parameter app name")
					.required(false)
					.example("component"))
			.response(buildResponse("200", ComponentsDTO.class))
			.build();
	}

	private void getCustomerOperations(org.springdoc.core.fn.builders.operation.Builder ops) {
		ops.operationId(CustomerRoute.class.getSimpleName())
			.summary("Get all routes")
			.beanClass(CustomerRoute.class)
			.beanMethod(CONNECTOR_REQUEST_MAPPING)
			.parameter(parameterBuilder()
					.name(COMPONENT_PARAMETER)
					.description("Set parameter app name")
					.required(false)
					.example("component"))
			.response(buildResponse("200", ComponentsDTO.class))
			.build();
	}
	
	private org.springdoc.core.fn.builders.apiresponse.Builder buildResponse(String statusCode, Class<?> clazz) {
		var builder = responseBuilder()
				.content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE))
				.responseCode(statusCode);
		
		if(clazz != null) {
			builder.implementation(clazz);
		}
		return builder;
	}
}
