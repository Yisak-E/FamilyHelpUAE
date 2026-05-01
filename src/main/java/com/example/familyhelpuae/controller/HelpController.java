package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.service.HelpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/help")
@RequiredArgsConstructor
public class HelpController {

    private final HelpService helpService;

    /**
     * 1. Get posts (Handles "ALL", "OFFER", or "SEEK" via the ?type parameter)
     * Example: GET /api/help/posts?type=OFFER
     */
    @GetMapping("/posts")
    public ResponseEntity<List<CommunityPost>> getPosts(@RequestParam(required = false) String type) {
        return ResponseEntity.ok(helpService.getPosts(type));
    }

    /**
     * 2. Create a new post (Handles both Offers and Requests in one payload)
     */
    @PostMapping("/posts")
    public ResponseEntity<CommunityPost> createPost(
            @Valid @RequestBody CreatePostDto createPostDto,
            Principal principal) {

        // principal.getName() extracts the email from your JWT token
        CommunityPost createdPost = helpService.createPost(createPostDto, principal.getName());
        return ResponseEntity.ok(createdPost);
    }

    /**
     * 3. Get my activities (Finds all posts made by the logged-in family)
     */
    @GetMapping("/my-activity")
    public ResponseEntity<List<CommunityPost>> getMyActivities(Principal principal) {
        return ResponseEntity.ok(helpService.getMyPosts(principal.getName()));
    }
}