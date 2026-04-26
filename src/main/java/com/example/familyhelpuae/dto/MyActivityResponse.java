package com.example.familyhelpuae.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyActivityResponse {

    private String type;
    private Long id;
    private String status ;
    private String category;
    private String description;
    private String familyName;

    //help request activity
    private String urgency;


}
