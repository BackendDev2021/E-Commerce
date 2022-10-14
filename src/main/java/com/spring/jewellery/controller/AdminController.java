package com.cyberazon.jewellery.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberazon.jewellery.dto.CategoryProductDto;
import com.cyberazon.jewellery.dto.ProductDto;
import com.cyberazon.jewellery.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(@RequestParam String emailId) {
		return new ResponseEntity<>(adminService.deleteUser(emailId), HttpStatus.OK);
	}

	@PostMapping("/category")
	public ResponseEntity<?> createCategory(@RequestParam String categoryName) {
		return new ResponseEntity<>(adminService.createCategory(categoryName), HttpStatus.OK);
	}

	@PostMapping("/category/name")
	public ResponseEntity<?> renameCategory(@Valid @RequestParam Long categoryId, String newCategoryName) {
		return new ResponseEntity<>(adminService.renameCategory(categoryId, newCategoryName), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCategory(@RequestParam Long categoryId) {
		return new ResponseEntity<>(adminService.deleteCategory(categoryId), HttpStatus.OK);
	}

	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
		return new ResponseEntity<>(adminService.createProduct(productDto), HttpStatus.OK);
	}

	@PutMapping("/product-update")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
		return new ResponseEntity<>(adminService.updateProduct(productDto), HttpStatus.OK);
	}

	@DeleteMapping("/product-delete")
	public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
		return new ResponseEntity<>(adminService.deleteProduct(productId), HttpStatus.OK);
	}

	@PostMapping("category-product")
	public ResponseEntity<?> createCategoryAndProducts(@RequestBody CategoryProductDto categoryProduct) {
		return new ResponseEntity<>(adminService.createCategoryAndProducts(categoryProduct), HttpStatus.OK);
	}

	@DeleteMapping("category-product/delete")
	public ResponseEntity<?> deleteCategoryAndProductsById(@RequestParam Long id) {
		return new ResponseEntity<>(adminService.deleteCategoryAndProductsById(id), HttpStatus.OK);
	}

	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategoriesAndProducts() {
		return new ResponseEntity<>(adminService.getAllCategoriesAndProducts(), HttpStatus.OK);
	}

	@DeleteMapping("category/products")
	public ResponseEntity<?> deleteAllCategoriesAndProducts() {
		return new ResponseEntity<>(adminService.deleteAllCategroiesAndProducts(), HttpStatus.OK);
	}
}
