package com.javaproject.customer.bff.dto;

public record UserDTO(
	Long id,
	String userLogin,
	String password,
	String userName,
	String userEmail,
	UserStatus userStatu)
	{
		public UserDTO() {this(null, "", "", "", "", new UserStatus());}
		
	public record UserStatus(
			String codigo,
			String descricao) 
		{
			public UserStatus() {this("", "");}
		}

}
