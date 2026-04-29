package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. Link directly to the unified CommunityPost instead of TaskTransaction
    // This supports your "One ID to rule them all" logic
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost post;

    // 2. Clearer naming for the Family relationships
    @ManyToOne
    @JoinColumn(name = "reviewer_family_id", nullable = false)
    private Family reviewerFamily;

    @ManyToOne
    @JoinColumn(name = "reviewed_family_id", nullable = false)
    private Family reviewedFamily;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // 3. Use LocalDateTime for consistency with TaskTransaction
    private LocalDateTime createdAt = LocalDateTime.now();
}