package com.example.familyhelpuae.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardResponse {
    private String familyName;
    private double reputationScore;
    private int completedInteractions;
    private int treesPlanted;

}
