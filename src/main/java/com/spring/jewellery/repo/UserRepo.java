package com.cyberazon.jewellery.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.cyberazon.jewellery.model.AppUser;

public interface UserRepo<T extends AppUser> extends CrudRepository<T, Long>, JpaSpecificationExecutor<AppUser> {

	Optional<AppUser> findByEmailId(String emailId);

	boolean existsById(String emailId);


}
