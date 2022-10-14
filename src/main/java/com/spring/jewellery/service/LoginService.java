package com.cyberazon.jewellery.service;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.cyberazon.jewellery.request.SignUpRequest;
import com.cyberazon.jewellery.response.AppResponse;
import com.cyberazon.jewellery.response.LoginResponse;

public interface LoginService {

	public AppResponse<LoginResponse> signUp(@Valid SignUpRequest request);

	public AppResponse<LoginResponse> login(String emailId, String password);
	
	public String activateUser(String emailId);

	public AppResponse<String> generateTempPassword(String emailId) throws MessagingException;

	

}
