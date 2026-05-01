package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "community_posts")
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family; // The Post Creator

    private String postType; // "OFFER" or "SEEK"
    private String title;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    // "OPEN" (Accepting Apps), "IN_PROGRESS" (Helper Selected), "COMPLETED", "CANCELLED"
    private String status = "OPEN";

    // Counter updated by ApplicationService to show popular requests
    private int applicationCount = 0;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Specific Metadata for the Family Calendar grid
    private String urgency;
    private String availability;
    private LocalDateTime neededBy;
}