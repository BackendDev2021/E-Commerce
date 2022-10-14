package com.cyberazon.jewellery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.cyberazon.jewellery.model.Product;

public interface ProductRepo<T extends Product>
		extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	Optional<Product> findByName(String name);

}
