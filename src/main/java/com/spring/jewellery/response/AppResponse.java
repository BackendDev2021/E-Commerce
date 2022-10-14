package com.cyberazon.jewellery.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponse<T>{

	private String status;
	private String statusCode;
	private T data;

}
