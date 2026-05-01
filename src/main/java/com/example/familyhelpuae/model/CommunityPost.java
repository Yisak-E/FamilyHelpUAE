package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "community_posts")
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @Column(nullable = false)
    private String postType; // "OFFER" or "SEEK"

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "OPEN"; // "OPEN", "IN_PROGRESS", "COMPLETED"
    private Date createdAt = new Date();

    // Specific to SEEK requests
    private String urgency;
    private Date neededBy;

    // Specific to OFFER requests
    private String availability;
}