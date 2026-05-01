package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one number, one uppercase, and one lowercase letter")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Family name is required")
    @Size(min = 3, message = "Family name must be at least 3 characters")
    private String familyName;

    // CHANGED: city to address to match Family.java
    @NotBlank(message = "Address is required")
    private String address;

    @Min(value = 1, message = "Family size must be at least 1")
    private int familySize;
}