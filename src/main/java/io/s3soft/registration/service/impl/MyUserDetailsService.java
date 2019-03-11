package io.s3soft.registration.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.s3soft.registration.model.User;
import io.s3soft.registration.repository.UserRepository;
@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//boolean enbled=true;
		boolean accountNonExpired=true;
		boolean credentialsNonExpired=true;
		boolean accountNonLocked=true;

		User user=userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(
					"No user found with username: "+ email);
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword().toLowerCase(),
				user.isEnabled(), 
				accountNonExpired, 
				credentialsNonExpired,
				accountNonLocked, 
				getAuthorities(user.getRoles())
				);
	}

	 private static List<GrantedAuthority> getAuthorities (List<String> roles) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        for (String role : roles) {
	            authorities.add(new SimpleGrantedAuthority(role));
	        }
	        return authorities;
	    }

}
