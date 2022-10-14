package com.cyberazon.jewellery.response;

import lombok.Data;

@Data
public class LoginResponse {

	private String token;
	private String message;
	private String userName;
	private String emailId;
	private String role;
	

	public LoginResponse() {
		super();
		
	}
}
