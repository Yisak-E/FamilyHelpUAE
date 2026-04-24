package com.example.familyhelpuae.model;

import jakarta.persistence.*;

@Entity
public class HelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_family_id")
    private Family requesterFamily;

    private String category;
    private String details;
    private String urgency;
    private String status = "OPEN";
}