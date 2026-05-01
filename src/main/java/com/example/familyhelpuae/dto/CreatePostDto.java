package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Date;

@Data
public class CreatePostDto {

    @NotBlank(message = "Post type is required (OFFER or SEEK)")
    private String postType;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Description is required")
    private String description;

    // Optional fields (Spring Boot will leave them null if not provided)
    private String urgency;
    private Date neededBy;
    private String availability;
}