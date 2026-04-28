package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.LeaderboardResponse;
import com.example.familyhelpuae.service.RewardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RewardController {
    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        /**
         * GET /api/rewards/status: View how many trees the family has earned based on their completed interactions.
         * GET /api/rewards/leaderboard: A public list of top-performing families to encourage community participation.
         */this.rewardService = rewardService;
    }

    @GetMapping("/api/rewards/status")
    public String status() {
        return "This is the reward status";
    }

    @GetMapping("/api/rewards/leaderboard")
    public ResponseEntity<List<LeaderboardResponse>> leaderboard() {
        return new ResponseEntity<>(rewardService.getLeaderboard(), HttpStatus.OK);
    }
}
