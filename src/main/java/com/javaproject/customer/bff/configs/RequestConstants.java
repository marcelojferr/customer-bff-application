package com.javaproject.customer.bff.configs;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class RequestConstants {
	
	public static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);
	public static final String COMPONENT_PARAMETER = "component";
	public static final String PAGE_PARAMETER = "page";
	public static final String SIZE_PARAMETER = "size";
	public static final String SORT_PARAMETER = "sort";
	
	private RequestConstants() {}
}
