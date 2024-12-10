package com.javaproject.customer.bff.configs;

import java.util.List;
public class ComponentConstants {

	public static final String APP_USER = "app-user";
	public static final String APP_CUSTOMER = "app-customer";
	
	public static final List<String> APP_COMPONENT_NAMES = List.of(
			APP_USER,
			APP_CUSTOMER);
	
	private ComponentConstants() {}
}
