package com.cyberazon.jewellery.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProductDto {

	private Long id;

	@NotBlank(message = "Product Name is mandatory")
	@Size(min = 4, max = 20)
	@Column(unique = true)
	private String name;

	@NotBlank(message = "Description is mandatory")
	private String description;

	@NotEmpty(message = "Weight cannot be empty")
	private String weight;

	private int oos_reorder;

	private int quantity;

}
