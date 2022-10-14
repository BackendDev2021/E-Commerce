package com.cyberazon.jewellery.dto;

import lombok.Data;

@Data
public class CategoryProductDto {

	private Long id;

	private CategoryDto category;

	private ProductDto products;
}
