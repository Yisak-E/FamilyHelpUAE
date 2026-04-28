package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRequestDto{
    @NotBlank(message = "Family name required")
    private String familyName;
    @NotBlank(message = "")
    private String category;
    @NotBlank(message = "")
    private String details;
    @NotBlank(message = "")
    private String status;
    @NotBlank(message = "")
    private String urgent;
    
    
}