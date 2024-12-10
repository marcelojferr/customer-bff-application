package com.javaproject.customer.bff.security.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class JwtTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
	private String scope;
	
	public JwtTokenResponse() {}
	
	public JwtTokenResponse(String accessToken, String tokenType, Integer expiresIn, String scope) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.scope = scope;
	}

	public JwtTokenResponse(JwtTokenResponse jwtTokenResponse) {
		this.accessToken = jwtTokenResponse.getAccessToken();
		this.tokenType = jwtTokenResponse.getTokenType();
		this.expiresIn = jwtTokenResponse.getExpiresIn();
		this.scope = jwtTokenResponse.getScope();
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}
	
	public void setExpireIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
}
