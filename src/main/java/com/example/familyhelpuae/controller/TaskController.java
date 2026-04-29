package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.model.TaskTransaction;
import com.example.familyhelpuae.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/accept/{postId}")
    public ResponseEntity<TaskTransaction> acceptTask(@PathVariable Long postId) {
        TaskTransaction transaction = taskService.acceptTask(postId);
        return ResponseEntity.ok(transaction);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskTransaction> completeTask(@PathVariable Long taskId) {
        TaskTransaction transaction = taskService.completeTask(taskId);
        return ResponseEntity.ok(transaction);
    }

    @PatchMapping("/{taskId}/cancel")
    public ResponseEntity<TaskTransaction> cancelTask(@PathVariable Long taskId) {
        TaskTransaction transaction = taskService.cancelTask(taskId);
        return ResponseEntity.ok(transaction);
    }
}