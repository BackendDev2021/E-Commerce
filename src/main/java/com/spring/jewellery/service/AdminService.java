package com.cyberazon.jewellery.service;

import org.springframework.web.multipart.MultipartFile;

import com.cyberazon.jewellery.dto.CategoryDto;
import com.cyberazon.jewellery.dto.CategoryProductDto;
import com.cyberazon.jewellery.dto.ProductDto;
import com.cyberazon.jewellery.response.AppResponse;

public interface AdminService {

	// Category
	AppResponse<CategoryDto> createCategory(String categoryName);

	AppResponse<CategoryDto> renameCategory(Long categoryId, String newCategoryName);

	AppResponse<String> deleteCategory(Long catetgoryId);

	// Product
	AppResponse<ProductDto> createProduct(ProductDto product);

	AppResponse<String> updateProduct(ProductDto product);

	AppResponse<String> deleteProduct(Long productId);

	AppResponse<String> upLoadProductImage(Long productId, MultipartFile file);

	AppResponse<String> deleteUser(String emailId);

	AppResponse<?> createCategoryAndProducts(CategoryProductDto categoryProduct);

	AppResponse<String> deleteCategoryAndProductsById(Long id);

	AppResponse<?> getAllCategoriesAndProducts();

	AppResponse<String> deleteAllCategroiesAndProducts();

}
