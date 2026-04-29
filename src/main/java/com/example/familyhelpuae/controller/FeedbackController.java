package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.CreateFeedbackDto;
import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * POST /api/feedback/submit
     * Matches the frontend api.createFeedback call
     */
    @PostMapping("/submit")
    public ResponseEntity<Feedback> submitFeedback(
            @Valid @RequestBody CreateFeedbackDto dto,
            Principal principal) {
        return ResponseEntity.ok(feedbackService.submitFeedback(dto, principal.getName()));
    }

    /**
     * GET /api/feedback/family/{familyId}
     * Matches the frontend api.getFeedbackForFamily call
     */
    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<Feedback>> getFeedbackForFamily(@PathVariable Long familyId) {
        return ResponseEntity.ok(feedbackService.getFeedbackForFamily(familyId));
    }
}