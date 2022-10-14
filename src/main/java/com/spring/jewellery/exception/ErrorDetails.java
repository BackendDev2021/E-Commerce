package com.cyberazon.jewellery.exception;

public enum ErrorDetails {

	// User Error codes
	USER_EXISTS(001, "User already exists with same email. Please login", "error_user_email_mobile_exists"),
	USER_NOT_FOUND(002, "User not found", "error_user_not_found"),
	MOBILE_NOT_VALID(003, "Please enter valid mobile number", "mobile_not_valid"),
	VALUES_NOT_FOUND(004, "Please enter the credentials or values", "values_not_found"),
	REQUEST_VALUE_NOT_FOUND(005, "Value cannot be blank", "request_value_not_found"),
	GENDER_ERROR(006, "Gender must be MALE,FEMALE,OTHERS", "gender_error"),
	RESOURCE_NOT_FOUND(007, "Requested resource not found", "resource_not_found"),
	ACCOUNT_ACTIVATION_ERROR(007, "Kinldy activate your account to continue", "account_activation_required"),

	// Category Error codes
	CATEGORY_NAME_EMPTY(101, "Category Name cannot be empty", "category_name_empty"),
	CATEGORY_ALREADY_EXISTS(102, "Category Name already exists", "category_name_exists"),
	CATEGORY_ID_EMPTY(103, "Category Id cannot be empty", "category_id_empty"),
	CATEGORY_NOT_FOUND(104, "Category not found", "category_not_found"),

	// Product Error codes
	PRODUCT_NOT_FOUND(105, "Product not found", "product_not_found"),
	PRODUCT_NAME_ALREADY_EXIST(106, "Product name already exist", "product_name_already_exist");

	private final int code;
	private final String message;
	private final String uiErrorKey;

	private ErrorDetails(int code, String description, String uiErrorKey) {
		this.code = code;
		this.message = description;
		this.uiErrorKey = uiErrorKey;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public String getUiErrorKey() {
		return uiErrorKey;
	}

}
