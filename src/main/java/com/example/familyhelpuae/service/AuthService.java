package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Handles new user registration, creates a family profile,
     * and instantly logs them in by returning a UserResponse with a JWT.
     */
    @Transactional
    public UserResponse register(SignupRequest dto) {
        // 1. Check if user already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        // 2. Create the Family Profile
        Family family = new Family();
        family.setFamilyName(dto.getFamilyName());
        family.setEmail(dto.getEmail());
        family.setTrustScore(5.0); // Baseline trust score
        family.setLastActive(LocalDateTime.now());

        // 3. Create the User Account
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        user.setFamily(family);

        // 4. Auto-verify the email to bypass the confirmation check
        user.setEmailVerified(true);
        user.setVerificationToken(null);

        // Save to DB first to generate the ID for the user and family
        userRepository.save(user);

        // 5. Generate JWT Token instantly for the new user
        String token = jwtService.generateToken(user);

        // 6. Build and return the UserResponse DTO
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .familyId(user.getFamily().getId())
                .familyName(user.getFamily().getFamilyName())
                .build();
    }

    /**
     * Handles login without checking for email verification.
     */
    public UserResponse login(LoginRequest dto) {
        // Find user
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        // Standard Authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // Update last active status
        user.getFamily().setLastActive(LocalDateTime.now());
        userRepository.save(user);

        // Generate and return JWT
        String token = jwtService.generateToken(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .familyId(user.getFamily().getId())
                .familyName(user.getFamily().getFamilyName())
                .build();
    }
}