package com.example.familyhelpuae.service;

import com.example.familyhelpuae.FamilyHelpUaeApplication;
import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.exception.EmailDublicationException;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    public UserResponse signup(SignupRequest dto) throws EmailDublicationException {

        //1. check if the email exist
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailDublicationException();
        }

        // 2. check if the family exists

        Family family = familyRepository.findByFamilyName(dto.getFamilyName()).orElse(null);

        if (family == null) {
            new Family();
            family = Family.builder()
                    .familyName(dto.getFamilyName())
                    .email(dto.getEmail())
                    .address(dto.getAddress())
                    .familySize(dto.getFamilySize())
                    .trustScore(5.0)
                    .completedInteractions(0)
                    .cancelledInteractions(0)
                    .lastActive(LocalDateTime.now())
                    .build();
        }


        // 3. Create the User Account
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("ROLE_USER", dto.getRole()));
        user.setFamily(family);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        user.setEmailVerified(true); // Automatically verify email

        User savedUser = userRepository.save(user);

        // 4. Generate JWT
        String token = jwtService.generateToken(savedUser);

        // 5. Return UserResponse
        return getBuild(savedUser, token);
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
        String jwtToken = jwtService.generateToken(user);
        // 4. Return the comprehensive UserResponse
        return getBuild(user, jwtToken);
    }

    private static UserResponse getBuild(User user, String jwtToken) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRoles().iterator().next())
                .accessToken(jwtToken)
                .refreshToken(jwtToken)
                .familyId(user.getFamily().getId())
                .familyName(user.getFamily().getFamilyName())
                .address(user.getFamily().getAddress())
                .trustScore(user.getFamily().getTrustScore())
                .completedInteractions(user.getFamily().getCompletedInteractions())
                .cancelledInteractions(user.getFamily().getCancelledInteractions())
                .build();
    }


    public List<User> allUser() {
        return userRepository.findAll();
    }
    
    public List<Family> allFamilies() {
        return familyRepository.findAll();
    }
    
    


}