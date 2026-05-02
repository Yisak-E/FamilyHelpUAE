package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.LoginRequest;
import com.example.familyhelpuae.dto.SignupRequest;
import com.example.familyhelpuae.dto.UserResponse;
import com.example.familyhelpuae.exception.EmailDublicationException;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            UserResponse response = authService.signup(signupRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmailDublicationException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            UserResponse resp = authService.login(request);
            return ResponseEntity.ok(resp);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/api/auth/allUser")
    public ResponseEntity<List<User>> allUser() {
        return ResponseEntity.ok(authService.allUser());
    }

    @GetMapping("/api/auth/allFamily")
    public ResponseEntity<List<Family>> allFamily() {
        return ResponseEntity.ok(authService.allFamilies());
    }
}