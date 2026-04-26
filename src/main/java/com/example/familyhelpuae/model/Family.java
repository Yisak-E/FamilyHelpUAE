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
  
    private String city;

  
    private int size;
  
    private Date marriedDate;
  
    private String marriedCountry;
  
    private double reputationScore = 5.0;
  
    private int completedInteractions = 0;
  
    private int treesPlanted = 0;

  
    @Version
    private Long version;

}