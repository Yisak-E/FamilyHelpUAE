package com.example.familyhelpuae.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyName;
    private String email;

    // --- Trust Score Metrics ---
    // The 1-10 result from the TrustScoreService
    private double trustScore = 5.0;

    // Reliability counters for the algorithm
    private int completedInteractions = 0;
    private int cancelledInteractions = 0;

    // Engagement tracking for the logarithmic weight
    private int totalApplicationsSent = 0;
    private LocalDateTime lastActive;

    // FCM Token for Push Notifications
    private String firebaseToken;

    @JsonIgnore
    @OneToOne(mappedBy = "family")
    private User user;
}