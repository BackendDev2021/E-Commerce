package com.cyberazon.jewellery.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.cyberazon.jewellery.exception.AppException;
import com.cyberazon.jewellery.exception.ErrorDetails;
import com.cyberazon.jewellery.model.AppUser;
import com.cyberazon.jewellery.request.SignUpRequest;
import com.cyberazon.jewellery.utils.Gender;
import com.cyberazon.jewellery.utils.Role;

@Mapper(componentModel = "spring")
public interface UserMapper {

	AppUser toAppUser(SignUpRequest request);

	@AfterMapping
	static void userSignUp(@MappingTarget AppUser user) {

		user.setActive(0);
		user.setId(user.getId());
		if (user.getEmailId().equalsIgnoreCase("mohan@cyberazon.com")) {
			user.setRole(Role.ADMIN);
		} else {
			user.setRole(Role.USER);
		}

		if (user.getGender().equalsIgnoreCase(Gender.MALE.toString())) {
			user.setGender("1");
		} else if (user.getGender().equalsIgnoreCase(Gender.FEMALE.toString())) {
			user.setGender("0");
		} else if (user.getGender().equalsIgnoreCase(Gender.OTHERS.toString())) {
			user.setGender("2");
		} else {
			throw new AppException(ErrorDetails.GENDER_ERROR);
		}

	}
}
