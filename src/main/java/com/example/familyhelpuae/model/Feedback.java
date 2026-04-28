package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "task_id")
    private SupportTask task;

    @ManyToOne
    @JoinColumn(name = "reviewer_family_id")
    private Family reviewer;


    @ManyToOne
    @JoinColumn(name = "reviewed_family_id")
    private Family reviewedFamily;

    private int rating;
    private String comment;
}