package com.javaproject.customer.bff.security.dto;

public class JwtTokenRequest {

	private String client_id;
	private String secret_id;
	private String grant_type;
		
	public JwtTokenRequest(String client_id, String secret_id, String grant_type) {
		super();
		this.client_id = client_id;
		this.secret_id = secret_id;
		this.grant_type = grant_type;
	}
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getSecret_id() {
		return secret_id;
	}
	public void setSecret_id(String secret_id) {
		this.secret_id = secret_id;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
}
