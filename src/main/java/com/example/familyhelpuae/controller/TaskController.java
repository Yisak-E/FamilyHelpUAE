package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.service.TaskService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        /**
         * POST /api/tasks/accept/{helpId}: When one family agrees to help another
         * PATCH /api/tasks/{taskId}/complete: Mark a task as done. This is the trigger for the reputation system
         * PATCH /api/tasks/{taskId}/cancel: For canceling an agreed interaction.
         */
        this.taskService = taskService;
    }
}
