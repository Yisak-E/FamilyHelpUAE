package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.LeaderboardResponse;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.repository.FamilyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardService {
    private FamilyRepository familyRepo;

    public RewardService(FamilyRepository familyRepository) {
        this.familyRepo = familyRepository;
    }

    public List<LeaderboardResponse> getLeaderboard() {
        List<LeaderboardResponse> leaderboardResponseList = new ArrayList<>();

        List<Family>  families = familyRepo.findAll();
        for (Family family : families) {
            leaderboardResponseList.add(
                    LeaderboardResponse.builder()
                            .familyName(family.getFamilyName())
                            .reputationScore(family.getReputationScore())
                            .completedInteractions(family.getCompletedInteractions())
                            .treesPlanted(family.getTreesPlanted())
                            .build()
            );
        }
        return leaderboardResponseList;
    }
}
