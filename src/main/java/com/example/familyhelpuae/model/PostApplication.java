package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class PostApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @ManyToOne
    @JoinColumn(name = "applicant_family_id")
    private Family applicantFamily;

    // Lifecycle: "PENDING", "ACCEPTED", "REJECTED", "CANCELLED"
    private String status = "PENDING";

    private LocalDateTime createdAt = LocalDateTime.now();

    // Captured when the creator clicks "Accept" to start the interaction
    private LocalDateTime selectionDate;
}