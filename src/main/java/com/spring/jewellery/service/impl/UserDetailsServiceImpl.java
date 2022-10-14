package com.cyberazon.jewellery.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazonaws.util.StringUtils;
import com.cyberazon.jewellery.communication.EmailServiceConfig;
import com.cyberazon.jewellery.communication.Mail;
import com.cyberazon.jewellery.exception.AppException;
import com.cyberazon.jewellery.exception.ErrorDetails;
import com.cyberazon.jewellery.mapper.UserMapper;
import com.cyberazon.jewellery.model.AppUser;
import com.cyberazon.jewellery.repo.UserRepo;
import com.cyberazon.jewellery.request.SignUpRequest;
import com.cyberazon.jewellery.response.AppResponse;
import com.cyberazon.jewellery.response.LoginResponse;
import com.cyberazon.jewellery.security.JwtTokenUtil;
import com.cyberazon.jewellery.service.LoginService;
import com.cyberazon.jewellery.service.Utility;
import com.cyberazon.jewellery.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, LoginService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	Utility utilityService;

	@Autowired
	UserRepo<AppUser> userRepo;

	@Autowired
	EmailServiceConfig emailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

		AppUser user = utilityService.getUser(emailId);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
		return new User(user.getEmailId(), user.getPassword(), authorities);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public AppResponse<LoginResponse> login(String emailId, String password) {

		if (com.amazonaws.util.StringUtils.isNullOrEmpty(emailId)
				&& com.amazonaws.util.StringUtils.isNullOrEmpty(password)) {
			log.error(Constants.VALUES_NOT_FOUND);
			throw new AppException(ErrorDetails.VALUES_NOT_FOUND);
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		AppUser user = utilityService.getUser(emailId);

		if (passwordEncoder.matches(password, user.getPassword())) {
			try {
				if (user.getActive() == 0) {
					throw new AppException(ErrorDetails.ACCOUNT_ACTIVATION_ERROR);
				}
				authenticate(emailId, password);
				return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, getLoginResponse(user));

			} catch (Exception e) {
				throw new AppException(ErrorDetails.ACCOUNT_ACTIVATION_ERROR);
			}
		}

		throw new AppException(ErrorDetails.USER_NOT_FOUND);
	}

	@Override
	public AppResponse<LoginResponse> signUp(@Valid SignUpRequest request) {

		Optional<AppUser> userRequest = userRepo.findByEmailId(request.getEmailId());
		if (userRequest.isPresent()) {
			log.error(Constants.USER_ALREADY_EXISTS);
			throw new AppException(ErrorDetails.USER_EXISTS);
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		AppUser user = userMapper.toAppUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setActivationCode(getRandomCode(20));
		userRepo.save(user);
		Mail mail = new Mail();
		mail.setMailTo(request.getEmailId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", user.getFirstName());
		mail.setProps(model);
		try {
			emailService.sendEmailForRegistration(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, getLoginResponse(user));
	}

	private LoginResponse getLoginResponse(AppUser user) {

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

		Map<String, Object> claims = new HashMap<>();
		claims.put("name", user.getFirstName());
		claims.put("role", user.getRole().name());

		final String token = jwtTokenUtil.generateToken(new User(user.getEmailId(), user.getPassword(), authorities),
				claims);

		LoginResponse response = new LoginResponse();
		response.setToken(token);
		response.setUserName(user.getFirstName());
		response.setEmailId(user.getEmailId());
		response.setRole(user.getRole().name());

		if (user.getForceChange() == 1) {
			response.setMessage(Constants.FORCE_PASSWORD);
		} else {
			response.setMessage(Constants.SUCCESS);
		}
		return response;
	}

	@Override
	public String activateUser(String emailId) {

		Optional<AppUser> existingUser = userRepo.findByEmailId(emailId);
		if (existingUser.isPresent()) {
			existingUser.get();
			existingUser.get().setActive(1);
			userRepo.save(existingUser.get());
			return Constants.ACTIVATE_USER;
		} else {
			throw new AppException(ErrorDetails.USER_NOT_FOUND);
		}
	}

	@Override
	public AppResponse<String> generateTempPassword(String emailId) throws MessagingException {
		if (StringUtils.isNullOrEmpty(emailId)) {
			throw new AppException(ErrorDetails.VALUES_NOT_FOUND);
		}
		Optional<AppUser> existingUser = userRepo.findByEmailId(emailId);
		if (existingUser.isPresent()) {
			String tempPassword = getRandomCode(7);
			// AppUser user = existingUser.get();
			// user.setPassword(new BCryptPasswordEncoder().encode(tempPassword));
			// user.setForceChange(1);
			// userRepo.save(user);
			existingUser.get().setPassword(new BCryptPasswordEncoder().encode(tempPassword));
			existingUser.get().setForceChange(1);
			userRepo.save(existingUser.get());
		}
		Mail mail = new Mail();
		mail.setMailTo(existingUser.get().getEmailId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", existingUser.get().getFirstName());
		model.put("activationCode", existingUser.get().getActivationCode());
		mail.setProps(model);
		emailService.sendEmailForForgetPassword(mail);
		// intentionally returning success always
		return new AppResponse<String>(Constants.SUCCESS, Constants.SUCCESS_CODE, Constants.TEMP_PASSWORD);
	}

	private String getRandomCode(int size) {
		return UUID.randomUUID().toString().substring(size);
	}
}
