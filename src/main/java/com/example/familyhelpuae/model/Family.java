package com.example.familyhelpuae.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "families")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Family {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnoreProperties({"offers", "requests", "members"})
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> members ;
  
    @Column(unique = true, nullable = false)
    private String familyName;

    private int size;
    private double reputationScore = 0.0;
    private int completedInteractions = 0;
    private String address;

    @Version
    private Long version;

}