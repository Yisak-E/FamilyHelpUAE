package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReputationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    private double previousScore;
    private double newScore;
    private String reason;
    private LocalDateTime updatedAt;
}