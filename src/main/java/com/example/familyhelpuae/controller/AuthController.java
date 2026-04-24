package com.example.familyhelpuae.controller;


import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.exception.EmailDublicationException;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private AuthService authService;
    private SignupRequest signupRequest;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    public User signup( @Valid @RequestBody SignupRequest signupRequest) throws EmailDublicationException {
        return  authService.signup(signupRequest);
    }

    @GetMapping("/auth/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request ) {
        return authService.login(request);
    }
}