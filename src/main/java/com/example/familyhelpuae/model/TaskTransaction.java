package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class TaskTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "application_id")
    private PostApplication application;

    // Redundant for quick calendar lookups
    private LocalDateTime scheduledDate; 
    
    private String status; // Mirrors the Post status once started
    private LocalDateTime completedAt;
}