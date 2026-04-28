package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.SupportTask;
import com.example.familyhelpuae.service.AuthService;
import com.example.familyhelpuae.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final AuthService authService;

    public TaskController(TaskService taskService, AuthService authService) {
        this.taskService = taskService;
        this.authService = authService;
    }

    /**
     * POST /api/tasks/accept/{helpId}: When one family agrees to help another.
     * This links the current user's active offer to the specific help request.
     */
    @PostMapping("/api/tasks/accept/{helpId}")
    public ResponseEntity<SupportTask> acceptTask(@PathVariable String helpId) {
        // Logic: Get the ID of the logged-in user who is accepting the task
        // Replace 'getCurrentUserId()' with your actual AuthService method
        Long currentUserId = authService.getCurrentUserId();

        // Assuming the user has a HelpOffer created, we pass that ID to the service
        // In a real scenario, you might find the offer by the user ID
        SupportTask task = taskService.acceptTask(helpId, currentUserId);
        return ResponseEntity.ok(task);
    }

    /**
     * PATCH /api/tasks/{taskId}/complete: Mark a task as done.
     * Triggers the transition of the request to 'CLOSED'.
     */
    @PatchMapping("/api/tasks/{taskId}/complete")
    public ResponseEntity<SupportTask> completeTask(@PathVariable String taskId) {
        SupportTask task = taskService.completeTask(taskId);
        return ResponseEntity.ok(task);
    }

    /**
     * PATCH /api/tasks/{taskId}/cancel: Cancels the agreed interaction.
     * Re-opens the original request for other families.
     */
    @PatchMapping("/api/tasks/{taskId}/cancel")
    public ResponseEntity<SupportTask> cancelTask(@PathVariable String taskId) {
        SupportTask task = taskService.cancelTask(taskId);
        return ResponseEntity.ok(task);
    }
}