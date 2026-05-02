package com.example.familyhelpuae.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProfileDto{
    private String familyName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate memberSince;
    private String phoneNumber;
}