package io.s3soft.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.s3soft.registration.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
	   @Query("select count(email) from io.s3soft.registration.model.User where email=?1")
	   int isEmailExist(String email);
	   User findByEmail(String email);
}
