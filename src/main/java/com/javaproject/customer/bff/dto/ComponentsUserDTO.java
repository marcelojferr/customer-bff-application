package com.javaproject.customer.bff.dto;

import static com.javaproject.customer.bff.configs.ComponentConstants.APP_USER;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ComponentsUserDTO {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, String> errors = new HashMap<>();
	
	@JsonProperty(APP_USER)
	private List<UserDTO> users;
	
	public ComponentsUserDTO() {}
	
	public ComponentsUserDTO( List<UserDTO> users) {
		this.users = users;
	}
	
	public List<UserDTO> getUsers(){
		return users;
	}

	public ComponentsUserDTO setUsers(List<UserDTO> users) {
		this.users = users;
		return this;
	}
	
	public Map<String, String> getErros(){
		return errors;
	}
	
	public ComponentsUserDTO setErrors( String key, String value) {
		this.errors.put(key, value);
		return this;
	}

}
