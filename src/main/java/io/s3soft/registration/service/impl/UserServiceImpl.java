package io.s3soft.registration.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.s3soft.registration.model.User;
import io.s3soft.registration.model.UserDto;
import io.s3soft.registration.model.VerificationToken;
import io.s3soft.registration.repository.UserRepository;
import io.s3soft.registration.repository.VerificationTokenRepository;
import io.s3soft.registration.service.IUserService;
@Service	
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRrepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenRepository tokenRepo;
	@Override
	public User registerNewUserAccount(UserDto accountDto) {
        User user=new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setAccountCreated(new Date());
        user.setRoles(Arrays.asList("USER"));
		return userRrepo.save(user);
	}

	@Override
	public boolean isEmailExist(String email) {
		return userRrepo.isEmailExist(email)==1?true:false;
	}

	@Override
	public Optional<User> findById(int userId) {
		return userRrepo.findById( userId);
	}

	@Override
	public List<User> findAll() {
		return userRrepo.findAll();
	}

	@Override
	public User updateUser(User user) {
		user.setAccountCreated(findById(user.getUserId()).get().getAccountCreated());
		user.setModifiedDate(new Date());
		return userRrepo.save(user);
	}

	@Override
	public void deleteUserById(int id) {
		userRrepo.deleteById(id);		
	}

	@Override
	public User getUser(String verificationToken) {
		return tokenRepo.findByToken(verificationToken).getUser();
	}

	@Override
	public VerificationToken getVerificationToken(String verificationToken) {
		return tokenRepo.findByToken(verificationToken);
	}

	@Override
	public void saveRegisteredUser(User user) {
		userRrepo.save(user);
	}

	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken verificationToken=new VerificationToken(token,user);
		tokenRepo.save(verificationToken);
	}

	@Override
	public User findByEmail(String userName) {
		return userRrepo.findByEmail(userName);
	}

}
