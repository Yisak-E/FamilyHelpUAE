package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "families")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String familyUniqueName;

    private String city;
    private String state;
    private String country;
    private int size;
    private Date marriedDate;
    private String marriedCountry;

    private double reputationScore = 5.0;
    private int completedInteractions = 0;
    private int treesPlanted = 0;

    @Version
    private Long version;
}