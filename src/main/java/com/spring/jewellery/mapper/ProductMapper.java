package com.cyberazon.jewellery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.cyberazon.jewellery.dto.ProductDto;
import com.cyberazon.jewellery.model.Product;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

	@Mapping(source = "productToSave.id", target = "id")
	@Mapping(source = "productToSave.name", target = "name")
	@Mapping(source = "productToSave.description", target = "description")
	@Mapping(source = "productToSave.weight", target = "weight")
	@Mapping(source = "productToSave.oos_reorder", target = "oos_reorder")
	@Mapping(source = "productToSave.quantity", target = "quantity")
	ProductDto toProductDto(Product productToSave);

	Product toProductDto(ProductDto productDto);
}
