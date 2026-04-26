package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOfferDto {
    @NotBlank(message = "")
    private String familyName;
    private String serviceCategory;
    @NotBlank(message = "")
    private String description;
    @NotBlank(message = "")
    private String status;

}
