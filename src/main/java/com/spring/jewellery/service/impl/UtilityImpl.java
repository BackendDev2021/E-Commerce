package com.cyberazon.jewellery.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberazon.jewellery.exception.AppException;
import com.cyberazon.jewellery.exception.ErrorDetails;
import com.cyberazon.jewellery.model.AppUser;
import com.cyberazon.jewellery.repo.UserRepo;
import com.cyberazon.jewellery.service.Utility;

@Service
public class UtilityImpl implements Utility {

	@Autowired
	private UserRepo<AppUser> userRepo;

	public AppUser getUser(String emailId) {
		try {

			Optional<AppUser> existingUser = userRepo.findByEmailId(emailId);

			if (existingUser.isPresent()) {
				return existingUser.get();
			}
			throw new AppException(ErrorDetails.USER_NOT_FOUND);

		} catch (Exception e) {
			throw new AppException(ErrorDetails.USER_NOT_FOUND);
		}

	}

}
