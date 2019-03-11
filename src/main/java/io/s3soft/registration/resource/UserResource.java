package io.s3soft.registration.resource;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import io.s3soft.registration.event.OnRegistrationEvent;
import io.s3soft.registration.exception.UserDataException;
import io.s3soft.registration.model.User;
import io.s3soft.registration.model.UserDto;
import io.s3soft.registration.model.VerificationToken;
import io.s3soft.registration.service.IUserService;
import io.s3soft.registration.validator.UserDtoValidator;
@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	private UserDtoValidator userDtoValidator;
	@Autowired
	private IUserService userService;

	@Autowired 
	private ApplicationEventPublisher eventPublisher;

	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> registerUserAccount(@RequestBody UserDto accountDto,WebRequest request,BindingResult result)throws UserDataException {
		userDtoValidator.validate(accountDto, result); 
		if(result.hasErrors()) {
			throw new UserDataException("Validations errors in user data ", result);
		}else {
			User registeredUser=userService.registerNewUserAccount(accountDto);	
			String appUrl=request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationEvent(registeredUser,appUrl));
			return new ResponseEntity<Optional<String>>(Optional.of(" A conformation link has been sent to  "+registeredUser.getEmail()+" Valid only for 10 minutes"),HttpStatus.CREATED);
		}
	}

	@GetMapping(value="/{userName}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("userName")String userName) {
		return ResponseEntity.ok(userService.findByEmail(userName));
	}

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Optional<List<User>>> getAllUser() {
		return ResponseEntity.ok(Optional.of((userService.findAll())));
	}

	@PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> UpdateUser(@RequestBody User user) {
		userService.updateUser(user);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping(value="/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Resource<String> deleteUser(@PathVariable("userId") int id){
		userService.deleteUserById(id);
		return new Resource<>("The User details has been removed ");
	}

	@GetMapping(value="/conformRegistration",produces=MediaType.APPLICATION_JSON_VALUE)
	public Resource<?> conformRegistration(@RequestParam("token")String verificationToken,@RequestParam("firstName")String firstName){
		VerificationToken vt=userService.getVerificationToken(verificationToken);
		User user=vt.getUser();
		Calendar cl=Calendar.getInstance();
		if((vt.getExpiryDate().getTime()-cl.getTime().getTime())<=0) {
			return  new Resource<String>("The link is expired... Click on resend link");
		}else {
		user.setEnabled(true);
		userService.saveRegisteredUser(user);
		return new Resource<String>("Hurray!!! Your account has been activated");
		}
	}

}
