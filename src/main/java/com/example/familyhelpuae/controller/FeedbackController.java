package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.Feedback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedbackController {
    public FeedbackController() {

        /**
         * POST /api/feedback/submit: Submit a rating (1-5 stars) and a comment for a completed task.
         * GET /api/feedback/family/{familyId}: View the feedback history for a specific family.
         */
    }

    @PostMapping("/api/feedback/submit")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        return null;
    }

    @GetMapping("/api/feedback/family/{familyId}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable("familyId") int familyId) {
        return null;
    }


}
