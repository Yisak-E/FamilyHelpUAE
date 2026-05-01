package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * POST /api/tasks
     * Create a new Help Request (SEEK) or Offer (OFFER)
     */
    @PostMapping
    public ResponseEntity<CommunityPost> createTask(@RequestBody CreatePostDto dto, Principal principal) {
        return ResponseEntity.ok(taskService.createTask(dto, principal.getName()));
    }

    /**
     * GET /api/tasks/feed
     * Uses Level C Criteria API for secure filtering and Redis for caching.
     */
    @GetMapping("/feed")
    public ResponseEntity<List<CommunityPost>> getCommunityFeed(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {

        return ResponseEntity.ok(taskService.getFilteredTasks(type, category, status));
    }

    /**
     * GET /api/tasks/{taskId}
     * Get details of a specific task.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<CommunityPost> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    /**
     * PATCH /api/tasks/{taskId}/complete
     * Marks the task as COMPLETED and triggers notification to leave a review.
     */
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable Long taskId, Principal principal) {
        taskService.completeTask(taskId, principal.getName());
        return ResponseEntity.ok("Task marked as completed. Please leave feedback!");
    }
}