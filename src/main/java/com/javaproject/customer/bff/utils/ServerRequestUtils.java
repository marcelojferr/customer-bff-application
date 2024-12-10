package com.javaproject.customer.bff.utils;

import java.util.List;
import java.util.Optional;
import static com.javaproject.customer.bff.configs.ComponentConstants.*;
import static com.javaproject.customer.bff.configs.RequestConstants.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.reactive.function.server.ServerRequest;

public class ServerRequestUtils {
	private static final Logger log = LoggerFactory.getLogger(ServerRequestUtils.class);
	
	public ServerRequestUtils() {}
	
	public static List<String> getListOfComponentNamesFromQueryParam(ServerRequest request){
		log.info("Listing component names from request");
		
		List<String> componentNames = APP_COMPONENT_NAMES;
		String componentParams = request.queryParam(COMPONENT_PARAMETER).orElse("");
		
		if(!componentParams.isEmpty() && !componentParams.equalsIgnoreCase("all")) {
			componentNames = List.of(componentParams.split(","));
		}
		
		return componentNames;
	}

	public static Pageable getPageableFromQueryParam(ServerRequest request) {
		int page;
		int size;
		Sort sort;
		Pageable pageable;
		Optional <String> optionalSort = request.queryParam(SORT_PARAMETER);

		try {
			page = Integer.parseInt(request.queryParam(PAGE_PARAMETER).orElse("0"));
		} catch (NumberFormatException e) {
			page = 0;
		}
		
		try {
			size = Integer.parseInt(request.queryParam(SIZE_PARAMETER).orElse("10"));
		} catch (NumberFormatException e) {
			size = 10;
		}
		
		pageable = PageRequest.of(page, size);
		
		if(optionalSort.isPresent() && !optionalSort.get().isEmpty()) {
			
			String direction = List.of(optionalSort.get().split(","))
				.stream()
				.filter (param -> Sort.Direction.fromOptionalString(param).isPresent())
				.findFirst()
				.orElse("ASC");
			
			List<Sort.Order> orders = List.of(request.queryParam(SIZE_PARAMETER).get().split(","))
				.stream()
				.filter (param -> Sort.Direction.fromOptionalString(param).isPresent())
				.map(field -> Sort.Order.by(field).with(Sort.Direction.fromString(direction)))
				.toList();
			
			sort = Sort.by(orders);
			
			pageable = PageRequest.of(page, size, sort);
		}
		
		return pageable;
	}
}
