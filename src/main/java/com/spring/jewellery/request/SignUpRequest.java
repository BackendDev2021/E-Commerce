package com.cyberazon.jewellery.request;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SignUpRequest {

	private Long id;

	@NotBlank(message = "Field is mandatory")
	@Size(min = 3, max = 30, message = "size must be 30")
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "must not contain special characters")
	private String firstName;

	@NotBlank(message = "Field is mandatory")
	@Size(min = 3, max = 30, message = "size must be 30")
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "must not contain special characters")
	private String lastName;

	@Email(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	@NotEmpty(message = "Email cannot be empty")
	@Size(min = 5, max = 30, message = "size must be 30")
	private String emailId;

	@NotBlank(message = "Field cannot be blank")
	@Pattern(regexp = "[a-zA-Z]+", message = "must contain aplhabetic values only")
	private String gender;

	@NotBlank(message = "Field is mandatory")
	@Size(min = 6, max = 8, message = "size must be 8")
	private String password;

	/*
	 * @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone
	 * = "UTC")
	 * 
	 * @Temporal(TemporalType.DATE) private String dob;
	 */
	@NotBlank(message = "Field is mandatory")
	private String address;

	@Size(min = 2, max = 28, message = "size must be 28")
	@Pattern(regexp = "[a-zA-Z]+", message = "must contain aplhabetic values only")
	private String city;

	@Pattern(regexp = "[a-zA-Z]+", message = "must contain aplhabetic values only")
	private String state;

	@Size(min = 6, max = 6, message = "size must be 6")
   	@Pattern(regexp = "[0-9]+", message = "allows only numeric values")
	private String code;

	@NotBlank(message = "Field cannot be blank")
	@Size(min = 10, max = 10, message = "size must be 10")
	@Pattern(regexp = "[0-9]+", message = "allows only numeric values")
	private String mobile;

}
