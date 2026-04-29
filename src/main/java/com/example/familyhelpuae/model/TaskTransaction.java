package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class TaskTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // REPLACE THE OLD OFFER/REQUEST FIELDS WITH THIS:
    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}