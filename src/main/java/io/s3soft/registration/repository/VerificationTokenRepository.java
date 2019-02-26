package io.s3soft.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.s3soft.registration.model.User;
import io.s3soft.registration.model.VerificationToken;
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
	VerificationToken findByToken(String verificationToken);
	VerificationToken findByUser(User user);
}
