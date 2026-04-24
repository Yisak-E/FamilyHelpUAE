package com.example.familyhelpuae.controller;


import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.exception.EmailDublicationException;
import com.example.familyhelpuae.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to Family Help UAE";
    }


    @PostMapping("/api/auth/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest signupRequest) throws EmailDublicationException {
        return new ResponseEntity<UserResponse>(authService.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request ) {
        return ResponseEntity.ok(authService.login(request));
    }
}