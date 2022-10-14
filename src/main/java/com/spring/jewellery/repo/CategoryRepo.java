package com.cyberazon.jewellery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.cyberazon.jewellery.model.Category;

public interface CategoryRepo<T extends Category> extends CrudRepository<T, Long>, JpaSpecificationExecutor<Category> {

	Optional<Category> findByName(String categoryName);

	Optional<T> findById(Long categoryId);

}
