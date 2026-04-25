package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.repository.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
        /*
         * POST /api/feedback/submit: Submit a rating (1-5 stars) and a comment for a completed task.
         * GET /api/feedback/family/{familyId}: View the feedback history for a specific family.
         */
    }

    @PostMapping("/api/feedback/submit")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        Feedback saved = feedbackRepository.save(feedback);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/api/feedback/family/{familyId}")
    public ResponseEntity<List<Feedback>> getFeedback(@PathVariable("familyId") Long familyId) {
        // For now return all feedbacks because there's no custom finder; filter in memory
        List<Feedback> all = feedbackRepository.findAll();
        List<Feedback> filtered = all.stream()
                .filter(f -> f.getReviewer() != null && f.getReviewer().getId() != null && f.getReviewer().getId().equals(familyId))
                .toList();
        return ResponseEntity.ok(filtered);
    }


}
