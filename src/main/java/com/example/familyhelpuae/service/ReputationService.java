package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.repository.FamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReputationService {

    private final FamilyRepository familyRepository;

    public ReputationService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    /**
     * Updates the family's reputation based on new feedback.
     * Logic: Increment reputation score and potentially update average rating.
     */
    @Transactional
    public void updateReputation(Long familyId, int rating) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("Family not found: " + familyId));

        // 1. Increment Reputation Score (representing tasks completed)
        int currentScore = family.getReputationScore() != null ? family.getReputationScore() : 0;
        family.setReputationScore(currentScore + 1);

        // 2. Logic for Tree Planting: Every 5 successful feedbacks, plant a tree
        if (family.getReputationScore() % 5 == 0) {
            int trees = family.getTreesPlanted() != null ? family.getTreesPlanted() : 0;
            family.setTreesPlanted(trees + 1);
        }

        familyRepository.save(family);
    }
}