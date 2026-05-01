package com.example.familyhelpuae.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String token;
    private String role;

    // Family specific details
    private Long familyId;
    private String familyName;
    private String address;

    // Stats for the frontend dashboard & profile
    private Double trustScore;
    private Integer completedInteractions;
    private Integer cancelledInteractions;
}