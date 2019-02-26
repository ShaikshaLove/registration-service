package io.s3soft.registration.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.s3soft.registration.model.UserDto;
import io.s3soft.registration.service.IUserService;
@Component
public class UserDtoValidator implements Validator {
	
	@Autowired
	private IUserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDto userDto=(UserDto)target;
		if(userDto.getFirstName().length()<=0) {
			errors.rejectValue("firstName", "", "First name should not be empty");
		}
		if(userDto.getLastName().length()<=0) {
			errors.rejectValue("lastName", "", "Last name should not be filled");
		}
		if(userDto.getPassword().length()<=0) {
			errors.rejectValue("password", "", "Provide a valid password");
		}else {
			if(!(userDto.getMatchingPassword().equals(userDto.getPassword()))) {
				errors.rejectValue("matchingPassword", "", "Password is not matched");
			}
		}
		if(userDto.getEmail().length()<=0) 
			errors.rejectValue("email", "", "Please provide a valid email");
		else if(userService.isEmailExist(userDto.getEmail())) {
			errors.rejectValue("email", "", "Email is registered already... please login");
		}
	}

}
