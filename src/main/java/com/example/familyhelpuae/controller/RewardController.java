package com.example.familyhelpuae.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardController {
    public RewardController() {
        /**
         * GET /api/rewards/status: View how many trees the family has earned based on their completed interactions.
         * GET /api/rewards/leaderboard: A public list of top-performing families to encourage community participation.
         */
    }

    @GetMapping("/api/rewards/status")
    public String status() {
        return "This is the reward status";
    }

    @GetMapping("/api/rewards/leaderboard")
    public String leaderboard() {
        return "This is the leaderboard";
    }
}
