package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(SignupRequest dto) {
        // 1. Check if user already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        // 2. Create the Family Profile
        Family family = new Family();
        family.setFamilyName(dto.getFamilyName());
        family.setEmail(dto.getEmail());
        family.setAddress(dto.getAddress());
        family.setFamilySize(dto.getFamilySize());
        family.setTrustScore(5.0); // Baseline trust score
        family.setLastActive(LocalDateTime.now());

        // 3. Create the User Account
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        user.setFamily(family);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        user.setEmailVerified(true); // Automatically verify email

        User savedUser = userRepository.save(user);

        // 4. Generate JWT
        String token = jwtService.generateToken((UserDetails) savedUser);

        // 5. Return UserResponse
        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .token(token)
                .familyId(savedUser.getFamily().getId())
                .familyName(savedUser.getFamily().getFamilyName())
                .address(savedUser.getFamily().getAddress())
                .trustScore(savedUser.getFamily().getTrustScore())
                .completedInteractions(savedUser.getFamily().getCompletedInteractions())
                .cancelledInteractions(savedUser.getFamily().getCancelledInteractions())
                .build();
    }

    public UserResponse login(LoginRequest request) {
        // 1. Authenticate the user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Fetch the user and their family details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // 3. Generate a new JWT token
        String jwtToken = jwtService.generateToken((UserDetails) user);

        // 4. Return the comprehensive UserResponse
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(jwtToken)
                .familyId(user.getFamily().getId())
                .familyName(user.getFamily().getFamilyName())
                .address(user.getFamily().getAddress())
                .trustScore(user.getFamily().getTrustScore())
                .completedInteractions(user.getFamily().getCompletedInteractions())
                .cancelledInteractions(user.getFamily().getCancelledInteractions())
                .build();
    }


}