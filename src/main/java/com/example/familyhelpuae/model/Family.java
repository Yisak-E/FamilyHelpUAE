package com.example.familyhelpuae.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "families")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String familyName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer familySize;

    // The community trust score (e.g., 6.3)
    @Column(nullable = false)
    private Double trustScore = 5.0; // Default starting score

    // Statistics for the dashboard
    @Column(nullable = false)
    private Integer completedInteractions = 0;

    @Column(nullable = false)
    private Integer cancelledInteractions = 0;

    // A family can have multiple users (e.g., parents, older children)
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<User> members;

    // A family can create many community posts
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<CommunityPost> posts;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private java.time.LocalDateTime lastActive;

}