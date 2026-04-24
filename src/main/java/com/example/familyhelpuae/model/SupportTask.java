package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SupportTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private HelpOffer offer;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private HelpRequest request;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}