package com.cyberazon.jewellery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.cyberazon.jewellery.dto.CategoryDto;
import com.cyberazon.jewellery.model.Category;



@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
	
	@Mapping(source="model.name", target="categoryName")
	@Mapping(source="model.id", target="categoryId")
	CategoryDto toCategoryDto(Category model);
	
	@Mapping(source="newCategoryName", target="categoryName")
	@Mapping(source="categoryId", target="categoryId")
	CategoryDto toRenameCategory(Long categoryId, String newCategoryName);
}
