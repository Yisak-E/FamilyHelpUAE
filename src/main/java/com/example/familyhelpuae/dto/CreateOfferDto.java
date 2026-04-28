package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOfferDto {
    @NotBlank(message = "family name(unique) required")
    private String familyName;
    @NotBlank(message = "Service Category is required")
    private String serviceCategory;
    @NotBlank(message = " description can't blank")
    private String description;
    @NotBlank(message = " status can't be  ignored")
    private String status;
}


