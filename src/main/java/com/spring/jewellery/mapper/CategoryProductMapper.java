package com.cyberazon.jewellery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.cyberazon.jewellery.dto.CategoryProductDto;
import com.cyberazon.jewellery.model.CategoryProduct;

@Mapper(componentModel = "spring")
@Component
public interface CategoryProductMapper {

	CategoryProduct toCategoryProductDto(CategoryProduct categoryAndProductToSave);

	@Mapping(source = "categoryProduct.id", target = "id")
	@Mapping(source = "categoryProduct.category", target = "category")
	@Mapping(source = "categoryProduct.category.categoryName", target = "category.name")
	@Mapping(source = "categoryProduct.products", target = "products")
	CategoryProduct toCategoryProductDto(CategoryProductDto categoryProduct);

	/*
	 * @AfterMapping static void setCorrespodingValues(@MappingTarget
	 * CategoryProduct categoryProduct) {
	 * categoryProduct.setCategory(categoryProduct.getCategory());
	 * categoryProduct.getCategory().setId(categoryProduct.getCategory().getId());
	 * categoryProduct.getCategory().setName(categoryProduct.getCategory().getName()
	 * ); categoryProduct.setProducts(categoryProduct.getProducts());
	 * categoryProduct.getProducts().setId(categoryProduct.getId());
	 * categoryProduct.getProducts().setName(categoryProduct.getProducts().getName()
	 * );
	 * categoryProduct.getProducts().setDescription(categoryProduct.getProducts().
	 * getDescription());
	 * categoryProduct.getProducts().setOos_reorder(categoryProduct.getProducts().
	 * getOos_reorder());
	 * categoryProduct.getProducts().setQuantity(categoryProduct.getProducts().
	 * getQuantity()); }
	 */
}
