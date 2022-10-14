package com.cyberazon.jewellery.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cyberazon.jewellery.request.LoginRequest;
import com.cyberazon.jewellery.request.SignUpRequest;
import com.cyberazon.jewellery.response.AppResponse;
import com.cyberazon.jewellery.response.LoginResponse;
import com.cyberazon.jewellery.service.LoginService;

@RestController
@RequestMapping("/authentication")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public AppResponse<LoginResponse> login(@RequestBody LoginRequest request) {
		return loginService.login(request.getEmailId(), request.getPassword());
	}

	@PostMapping("/signup")
	public AppResponse<LoginResponse> signUp(@Valid @RequestBody SignUpRequest request) {
		return loginService.signUp(request);
	}

	@GetMapping("/forgot-password")
	public ResponseEntity<?> generateTempPassword(@Valid @RequestParam String emailId) throws MessagingException {
		return new ResponseEntity<>(loginService.generateTempPassword(emailId), HttpStatus.OK);
	}

	@GetMapping("/activate")
	public ModelAndView activateUser(@RequestParam String emailId) throws IOException {
		loginService.activateUser(emailId);
		return new ModelAndView("account-activation");
	}

}
