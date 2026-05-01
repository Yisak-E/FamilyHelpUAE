
package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.LeaderboardResponse;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final FamilyRepository familyRepo;

    /**
     * Fetches data for the frontend leaderboard.
     * We use @Cacheable because fetching the entire family list can be
     * expensive as the community grows.
     */
    @Cacheable(value = "leaderboard")
    public List<LeaderboardResponse> getLeaderboard() {
        List<Family> families = familyRepo.findAll();

        return families.stream()
                .map(family -> LeaderboardResponse.builder()
                        .familyName(family.getFamilyName())
                        // Updated to use the new Trust Score from our NLP algorithm
                        .trustScore(family.getTrustScore())
                        .completedInteractions(family.getCompletedInteractions())
                        .lastActive(family.getLastActive())
                        .build())
                .collect(Collectors.toList());
    }
}
