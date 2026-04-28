package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//@Table(uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"provider_family_id", "serviceCategory", "isActive"})
//})
@Entity
@Setter
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"requester_family_id", "category", "status"})})
public class HelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_family_id")
    private Family requesterFamily;

    private String category;
    private String details;
    private String urgent;
    private String status = "OPEN";
}