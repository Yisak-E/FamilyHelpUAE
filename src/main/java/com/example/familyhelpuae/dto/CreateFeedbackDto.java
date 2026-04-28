package com.example.familyhelpuae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateFeedbackDto {
    @NotBlank(message="Task Id required ")
    private String taskId;

    @NotBlank(message="Reviewer family name required")
    private String reviewerFamilyName;

    @NotBlank(message=" rating required")
    private int rating;

    @NotBlank(message="Comment required")
    private String comment;

}
