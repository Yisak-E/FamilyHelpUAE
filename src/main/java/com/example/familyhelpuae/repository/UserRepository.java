package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used for login and checking if an account already exists
    Optional<User> findByEmail(String email);

    // The missing piece for your Email Confirmation System!
    Optional<User> findByVerificationToken(String token);
}