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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private UserRepository userRepo;
    private FamilyRepository familyRepo;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepo = userRepository;
        this.familyRepo = familyRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    @Transactional
    public UserResponse signup(SignupRequest request) throws EmailDublicationException {
        User user = new User();

        // check the user registered already
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new EmailDublicationException();
        }

        // Family Existence / Creation

        Family family = familyRepo.findByFamilyName(request.getFamilyName())
                .orElseGet(()-> {
                    Family newFamily = new Family();

                    newFamily.setFamilyName(request.getFamilyName());
                    newFamily.setCity(request.getCity());
                    newFamily.setSize(1);
                    return familyRepo.save(newFamily);
                });

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword( passwordEncoder.encode(request.getPassword()));// encrypt the password
        user.setFamily(family);
        user.setRole(request.getRole());

        User savedUser = userRepo.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .familyId(family.getId())
                .familyName(family.getFamilyName())
                .reputationScore(family.getReputationScore())
                .treesPlanted(family.getTreesPlanted())
                .build();

    }



    public UserResponse login(LoginRequest request) throws UsernameNotFoundException, BadCredentialsException {
         User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));


         if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
             throw new BadCredentialsException("Invalid email or password2");
         }

         Family family = user.getFamily();

         return UserResponse.builder()
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
