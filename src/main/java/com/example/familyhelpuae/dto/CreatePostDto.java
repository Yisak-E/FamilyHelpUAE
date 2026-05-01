package com.example.familyhelpuae.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreatePostDto {
    private String postType; // "OFFER" or "SEEK"
    private String title;
    private String category;
    private String description;
    private String urgency;
    private LocalDateTime neededBy;
}