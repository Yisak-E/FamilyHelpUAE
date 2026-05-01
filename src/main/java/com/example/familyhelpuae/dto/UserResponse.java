package com.example.familyhelpuae.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;

    private Long familyId;
    private String familyName;
    private double reputationScore;
}