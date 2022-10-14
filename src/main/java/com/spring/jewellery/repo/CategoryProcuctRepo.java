package com.cyberazon.jewellery.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.cyberazon.jewellery.model.CategoryProduct;

public interface CategoryProcuctRepo<T extends CategoryProduct>
		extends CrudRepository<T, Long>, JpaSpecificationExecutor<CategoryProduct> {

}
