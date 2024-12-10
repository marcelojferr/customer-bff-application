package com.javaproject.customer.bff.dto;

import static com.javaproject.customer.bff.configs.ComponentConstants.APP_CUSTOMER;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ComponentsCustomerDTO {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, String> errors = new HashMap<>();
	
	@JsonProperty(APP_CUSTOMER)
	private List<CustomerDTO> customers;
	
	public ComponentsCustomerDTO() {}
	
	public ComponentsCustomerDTO( List<CustomerDTO> customers) {
		this.customers = customers;
	}
	
	public List<CustomerDTO> getCustomers(){
		return customers;
	}

	public ComponentsCustomerDTO setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
		return this;
	}
	
	public Map<String, String> getErros(){
		return errors;
	}
	
	public ComponentsCustomerDTO setErrors( String key, String value) {
		this.errors.put(key, value);
		return this;
	}

}
