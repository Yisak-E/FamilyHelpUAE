package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.service.AuthService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    public TaskController(AuthService authService) {
        /**
         * POST /api/tasks/accept/{helpId}: When one family agrees to help another
         * PATCH /api/tasks/{taskId}/complete: Mark a task as done. This is the trigger for the reputation system
         * PATCH /api/tasks/{taskId}/cancel: For canceling an agreed interaction.
         */
    }
    @PostMapping("/api/tasks/accept/{helpId}")
    public String acceptTask(@PathVariable String helpId) {
        return "This is task: accept call";
    }

    @PatchMapping("/api/tasks/{taskId}/complete")
    public String completeTask(@PathVariable String taskId) {
        return "This is task:  complete call";
    }

    @PatchMapping("/api/tasks/{taskId}/cancel")
    public String cancelTask(@PathVariable String taskId) {
        return "This is task:   cancel call";
    }
}
