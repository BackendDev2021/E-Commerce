package com.cyberazon.jewellery.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberazon.jewellery.dto.CategoryDto;
import com.cyberazon.jewellery.dto.CategoryProductDto;
import com.cyberazon.jewellery.dto.ProductDto;
import com.cyberazon.jewellery.exception.AppException;
import com.cyberazon.jewellery.exception.ErrorDetails;
import com.cyberazon.jewellery.mapper.CategoryMapper;
import com.cyberazon.jewellery.mapper.CategoryProductMapper;
import com.cyberazon.jewellery.mapper.ProductMapper;
import com.cyberazon.jewellery.model.AppUser;
import com.cyberazon.jewellery.model.Category;
import com.cyberazon.jewellery.model.CategoryProduct;
import com.cyberazon.jewellery.model.Product;
import com.cyberazon.jewellery.repo.CategoryProcuctRepo;
import com.cyberazon.jewellery.repo.CategoryRepo;
import com.cyberazon.jewellery.repo.ProductRepo;
import com.cyberazon.jewellery.repo.UserRepo;
import com.cyberazon.jewellery.response.AppResponse;
import com.cyberazon.jewellery.service.AdminService;
import com.cyberazon.jewellery.service.Utility;
import com.cyberazon.jewellery.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CategoryRepo<Category> categoryRepo;

	@Autowired
	private ProductRepo<Product> productRepo;

	@Autowired
	private UserRepo<AppUser> userRepo;

	@Autowired
	private CategoryProcuctRepo<CategoryProduct> categoryProcuctRepo;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CategoryProductMapper categoryProductMapper;

	@Autowired
	Utility utilityService;

	@Override
	public AppResponse<CategoryDto> createCategory(String categoryName) {

		if (StringUtils.isEmpty(categoryName)) {
			log.error(Constants.CATEGORY_NAME_EMPTY);
			throw new AppException(ErrorDetails.CATEGORY_NAME_EMPTY);
		}
		Optional<Category> existingCategory = categoryRepo.findByName(categoryName);
		if (existingCategory.isPresent()) {
			log.error(Constants.CATEGORY_ALREADY_EXISTS);
			throw new AppException(ErrorDetails.CATEGORY_ALREADY_EXISTS);
		} else {
			Category categoryToSave = new Category(categoryName);
			categoryRepo.save(categoryToSave);
			return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE,
					categoryMapper.toCategoryDto(categoryToSave));
		}
	}

	// Rename the category by using category id with New category name
	@Override
	public AppResponse<CategoryDto> renameCategory(Long categoryId, String newCategoryName) {

		if (StringUtils.isBlank(newCategoryName)) {
			log.error(Constants.CATEGORY_NAME_EMPTY);
			throw new AppException(ErrorDetails.CATEGORY_NAME_EMPTY);
		}

		Category renameCategory = getCategoryById(categoryId);
		renameCategory.setName(newCategoryName);
		categoryRepo.save(renameCategory);
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE,
				categoryMapper.toRenameCategory(categoryId, newCategoryName));
	}

	// delete the category
	@Override
	public AppResponse<String> deleteCategory(Long categoryId) {
		categoryRepo.deleteById(categoryId);
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, categoryId + Constants.DELETE);
	}

	// Create the product
	@Override
	public AppResponse<ProductDto> createProduct(ProductDto productDto) {
		Optional<Product> existingProductName = productRepo.findByName(productDto.getName());
		if (existingProductName.isPresent()) {
			log.error(Constants.PRODUCT_NAME_ALREADY_EXISTS);
			throw new AppException(ErrorDetails.PRODUCT_NAME_ALREADY_EXIST);
		}
		Product productToSave = productMapper.toProductDto(productDto);
		productRepo.save(productToSave);
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, productMapper.toProductDto(productToSave));
	}

	// Update the product using product id
	@Override
	public AppResponse<String> updateProduct(ProductDto productDto) {
		getProductById(productDto.getId());
		Product product = productMapper.toProductDto(productDto);
		productRepo.save(product);
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, productDto.getName() + Constants.UPDATED);
	}

	// For delete product by product id
	@Override
	public AppResponse<String> deleteProduct(Long productId) {
		productRepo.deleteById(productId);
		return new AppResponse<String>(Constants.SUCCESS, Constants.SUCCESS_CODE, productId + Constants.DELETE);
	}

	// Method for delete the user
	@Override
	public AppResponse<String> deleteUser(String emailId) {
		if (StringUtils.isEmpty(emailId)) {
			log.error(Constants.VALUES_NOT_FOUND);
			throw new AppException(ErrorDetails.VALUES_NOT_FOUND);
		}
		AppUser existingUser = utilityService.getUser(emailId);
		userRepo.delete(existingUser);
		return new AppResponse<String>(Constants.SUCCESS, Constants.SUCCESS_CODE,
				existingUser.getEmailId() + Constants.DELETE);
	}

	@Override
	public AppResponse<String> upLoadProductImage(Long productId, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	public Product getProductById(Long productId) {

		Optional<Product> existById = productRepo.findById(productId);

		if (existById.isPresent()) {
			return existById.get();
		}
		throw new AppException(ErrorDetails.PRODUCT_NOT_FOUND);
	}

	public Category getCategoryById(Long categoryId) {

		Optional<Category> existById = categoryRepo.findById(categoryId);

		if (existById.isPresent()) {
			return existById.get();
		}
		throw new AppException(ErrorDetails.CATEGORY_NOT_FOUND);

	}

	@Override
	public AppResponse<?> createCategoryAndProducts(CategoryProductDto categoryProduct) {
		CategoryProduct categoryAndProductToSave = categoryProductMapper.toCategoryProductDto(categoryProduct);
		categoryRepo.save(categoryAndProductToSave.getCategory());
		productRepo.save(categoryAndProductToSave.getProducts());
		categoryProcuctRepo.save(categoryAndProductToSave);
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE,
				categoryProductMapper.toCategoryProductDto(categoryAndProductToSave));
	}

	@Override
	public AppResponse<String> deleteCategoryAndProductsById(Long id) {
		categoryProcuctRepo.deleteById(id);
		return new AppResponse<String>(Constants.SUCCESS, Constants.SUCCESS_CODE, id + Constants.DELETE);
	}

	@Override
	public AppResponse<?> getAllCategoriesAndProducts() {
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, categoryProcuctRepo.findAll());
	}

	@Override
	public AppResponse<String> deleteAllCategroiesAndProducts() {
		categoryProcuctRepo.deleteAll();
		categoryRepo.deleteAll();
		productRepo.deleteAll();
		return new AppResponse<>(Constants.SUCCESS, Constants.SUCCESS_CODE, "All records are Deleted");
	}
}
