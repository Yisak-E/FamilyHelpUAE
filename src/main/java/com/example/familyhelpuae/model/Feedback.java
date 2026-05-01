package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @ManyToOne
    @JoinColumn(name = "reviewer_family_id")
    private Family reviewerFamily;

    @ManyToOne
    @JoinColumn(name = "reviewed_family_id")
    private Family reviewedFamily;

    private int numericalRating; // 1-5 Star UI input

    @Column(columnDefinition = "TEXT")
    private String comment; // Analyzed by CoreNLP

    private double sentimentScore; // 1-10 result from TrustScoreService

    private LocalDateTime createdAt = LocalDateTime.now();
}