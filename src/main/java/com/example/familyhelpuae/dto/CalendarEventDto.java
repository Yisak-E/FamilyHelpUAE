package com.example.familyhelpuae.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CalendarEventDto {
    private Long postId;
    private String title;
    private String category;
    private LocalDateTime scheduledTime;
    private String status;
    private String role; // "HELPER" or "RECEIVER"
    private String otherFamilyName;
}