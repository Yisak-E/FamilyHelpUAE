package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/submit/{postId}")
    public ResponseEntity<String> submitFeedback(
            @PathVariable Long postId,
            @RequestBody String comment,
            @RequestParam int rating,
            Principal principal) {
        feedbackService.processFeedback(postId, principal.getName(), comment, rating);
        return ResponseEntity.ok("Feedback submitted and Trust Score updated.");
    }
}