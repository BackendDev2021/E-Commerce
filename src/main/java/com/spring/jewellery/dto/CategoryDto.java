package com.cyberazon.jewellery.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryDto {

	private Long categoryId;
	
	@NotBlank(message = "Category name is Mandatory to create")
	private String categoryName;
	
}
