package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "families")
public class Family {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

    @Setter
    @Getter
    @Column(unique = true, nullable = false)
    private String familyName;

    @Setter
    @Getter
    private String city;


    @Setter
    @Getter
    private int size;

    @Setter
    @Getter
    private Date marriedDate;

    @Setter
    @Getter
    private String marriedCountry;

    @Setter
    @Getter
    private double reputationScore = 5.0;

    @Setter
    @Getter
    private int completedInteractions = 0;

    @Setter
    @Getter
    private int treesPlanted = 0;


    @Setter
    @Getter
    @Version
    private Long version;

}