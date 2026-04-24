package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.service.AuthService;

public class TaskController {
    public TaskController(AuthService authService) {
        /**
         * POST /api/tasks/accept/{helpId}: When one family agrees to help another
         * PATCH /api/tasks/{taskId}/complete: Mark a task as done. This is the trigger for the reputation system
         * PATCH /api/tasks/{taskId}/cancel: For canceling an agreed interaction.
         */
    }
}
