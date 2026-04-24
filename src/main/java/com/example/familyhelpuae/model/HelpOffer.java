package com.example.familyhelpuae.model;

import jakarta.persistence.*;

@Entity
public class HelpOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_family_id")
    private Family providerFamily;

    private String serviceCategory;
    private String description;
    private boolean isActive = true;
}