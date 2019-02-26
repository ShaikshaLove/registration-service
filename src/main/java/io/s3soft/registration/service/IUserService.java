package io.s3soft.registration.service;

import java.util.List;
import java.util.Optional;

import io.s3soft.registration.model.User;
import io.s3soft.registration.model.UserDto;
import io.s3soft.registration.model.VerificationToken;

public interface IUserService {
	public User registerNewUserAccount(UserDto accountDto);
	boolean isEmailExist(String email);
	public Optional<User> findById(int userId);
	public List<User> findAll();
	public User updateUser(User user);
	public void deleteUserById(int id);
	public User findByEmail(String userName);
	//new methods
	public User getUser(String verificationToken);
	public VerificationToken getVerificationToken(String verificationToken);
	public void saveRegisteredUser(User user);
	public void createVerificationToken(User user,String token);
}
