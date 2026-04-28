package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.exception.EmailDublicationException;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JWTservice jwtService;
    private final UserRepository userRepo;
    private final FamilyRepository familyRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, FamilyRepository familyRepository, JWTservice jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.familyRepo = familyRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder; // Use the injected bean from your SecurityConfig
    }

    @Transactional
    public UserResponse signup(SignupRequest request) throws EmailDublicationException {
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new EmailDublicationException();
        }

        Family family = familyRepo.findByFamilyName(request.getFamilyName())
                .orElseGet(() -> {
                    Family newFamily = new Family();
                    newFamily.setFamilyName(request.getFamilyName());
                    newFamily.setCity(request.getCity());
                    newFamily.setSize(1);
                    newFamily.setReputationScore(0); // Initialize default values
                    newFamily.setTreesPlanted(0);
                    return familyRepo.save(newFamily);
                });

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFamily(family);
        user.setRole(request.getRole());

        User savedUser = userRepo.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());

        return mapToUserResponse(savedUser, token);
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return mapToUserResponse(user, token);
    }

    /**
     * Retrieves the ID of the currently authenticated user from the Security Context.
     */
    public Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in context"));
    }

    // Helper method to keep code DRY (Don't Repeat Yourself)
    private UserResponse mapToUserResponse(User user, String token) {
        Family family = user.getFamily();
        return UserResponse.builder()
                .token(token)
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .familyId(family.getId())
                .familyName(family.getFamilyName())
                .reputationScore(family.getReputationScore())
                .treesPlanted(family.getTreesPlanted())
                .build();
    }
}