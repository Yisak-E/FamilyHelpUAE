package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.PostApplication;
import com.example.familyhelpuae.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * POST /api/applications/apply/{postId}
     * Triggered by the "Apply" button on the Community Feed.
     */
    @PostMapping("/apply/{postId}")
    public ResponseEntity<PostApplication> applyToPost(@PathVariable Long postId, Principal principal) {
        return ResponseEntity.ok(applicationService.applyToPost(postId, principal.getName()));
    }

    /**
     * GET /api/applications/post/{postId}
     * Returns all applications for a specific post.
     * Used by the Post Creator to see who applied.
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostApplication>> getApplicantsForPost(@PathVariable Long postId, Principal principal) {
        return ResponseEntity.ok(applicationService.getApplicantsForPost(postId, principal.getName()));
    }

    /**
     * PATCH /api/applications/{applicationId}/accept
     * Triggered when the Post Creator selects a family.
     */
    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<PostApplication> acceptApplication(@PathVariable Long applicationId, Principal principal) {
        return ResponseEntity.ok(applicationService.acceptApplication(applicationId, principal.getName()));
    }

    /**
     * DELETE /api/applications/{applicationId}/cancel
     * Allows an applicant to withdraw their application while it's still PENDING.
     */
    @DeleteMapping("/{applicationId}/cancel")
    public ResponseEntity<Void> cancelApplication(@PathVariable Long applicationId, Principal principal) {
        applicationService.cancelApplication(applicationId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}