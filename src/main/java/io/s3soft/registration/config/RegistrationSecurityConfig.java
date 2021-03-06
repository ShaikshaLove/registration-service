package io.s3soft.registration.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.s3soft.registration.service.impl.MyUserDetailsService;

@EnableWebSecurity
public class RegistrationSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserDetailsService userDetailsService;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.authenticationProvider(authProvider());
	}
	private AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider ();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());		
		return authProvider;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().httpBasic().disable()
        .authorizeRequests().anyRequest().permitAll();  }
   
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

}
