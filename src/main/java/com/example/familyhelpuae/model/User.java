package com.example.familyhelpuae.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // --- Advanced Features Integration ---

    // Status for the Email Confirmation System
    private boolean isEmailVerified = false;
    private String verificationToken;

    // Level C Security: Tracking login attempts to prevent Brute Force
    private int loginAttempts = 0;
    private boolean isLocked = false;

    // --- Relationships ---

    // Direct link to the Family metrics (Trust Score, etc.)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    private Family family;

    // Roles for Spring Security (e.g., ROLE_USER, ROLE_ADMIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles = new HashSet<>();
}