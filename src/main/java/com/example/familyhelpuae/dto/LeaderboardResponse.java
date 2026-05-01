package com.example.familyhelpuae.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class LeaderboardResponse {
    private String familyName;
    private double trustScore; // Changed from reputationScore
    private int completedInteractions;
    private LocalDateTime lastActive; // Useful for the frontend "Activity" filter
}