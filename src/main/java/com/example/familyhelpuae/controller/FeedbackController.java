package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.repository.FeedbackRepository;
import com.example.familyhelpuae.service.ReputationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final ReputationService reputationService;

    public FeedbackController(FeedbackRepository feedbackRepository, ReputationService reputationService) {
        this.feedbackRepository = feedbackRepository;
        this.reputationService = reputationService;
    }

    /**
     * POST /api/feedback/submit: Submit a rating (1-5 stars) and a comment.
     * Also triggers the reputation update for the family being reviewed.
     */
    @PostMapping("/submit")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        // 1. Save the feedback record
        Feedback saved = feedbackRepository.save(feedback);

        // 2. Trigger reputation update for the family that received the help
        // We assume the Feedback model has a reference to the 'reviewedFamily'
        if (feedback.getReviewer() != null) {
            reputationService.updateReputation(
                    feedback.getReviewedFamily().getId(),
                    feedback.getRating()
            );
        }

        return ResponseEntity.ok(saved);
    }

    /**
     * GET /api/feedback/family/{familyId}: View the feedback history for a specific family.
     */
    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<Feedback>> getFeedback(@PathVariable("familyId") Long familyId) {
        // Ideally, you should add 'findByReviewedFamilyId' to your FeedbackRepository
        // For now, filtering the list as requested
        List<Feedback> all = feedbackRepository.findAll();
        List<Feedback> filtered = all.stream()
                .filter(f -> f.getReviewedFamily() != null && f.getReviewedFamily().getId().equals(familyId))
                .toList();

        return ResponseEntity.ok(filtered);
    }
}