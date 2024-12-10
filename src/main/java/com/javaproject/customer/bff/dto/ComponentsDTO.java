package com.javaproject.customer.bff.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import static com.javaproject.customer.bff.configs.ComponentConstants.*;

@Component
public class ComponentsDTO {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, String> errors = new HashMap<>();
	
	@JsonProperty(APP_USER)
	private List<UserDTO> users;
	
	@JsonProperty(APP_CUSTOMER)
	private List<CustomerDTO> customers;
	
	public ComponentsDTO() {}
	
	public ComponentsDTO(List<UserDTO> users, List<CustomerDTO> customers) {
		this.users = users;
		this.customers = customers;
	}
	
	public List<UserDTO> getUsers(){
		return users;
	}

	public ComponentsDTO setUsers(List<UserDTO> users) {
		this.users = users;
		return this;
	}

	public List<CustomerDTO> getCustomers(){
		return customers;
	}

	public ComponentsDTO setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
		return this;
	}
	
	public Map<String, String> getErros(){
		return errors;
	}
	
	public ComponentsDTO setErrors( String key, String value) {
		this.errors.put(key, value);
		return this;
	}
}
