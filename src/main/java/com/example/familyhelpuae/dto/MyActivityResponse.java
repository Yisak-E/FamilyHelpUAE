package com.example.familyhelpuae.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MyActivityResponse {
    private Long id;
    private String type; // OFFER or REQUEST
    private String category;
    private String title; // Added to match frontend
    private String description;
    private String status;
    private String urgency; // Used if type is REQUEST
    private LocalDateTime createdAt; // Added for frontend timeline sorting
}