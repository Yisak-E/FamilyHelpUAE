package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.TaskTransaction;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.TaskTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskTransactionRepository taskTransactionRepository;
    private final CommunityPostRepository communityPostRepository;

    @Transactional
    public TaskTransaction acceptTask(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Community post not found"));

        if (!post.getStatus().equals("OPEN")) {
            throw new RuntimeException("This post is no longer available.");
        }

        // 1. Update the Post status so others can't accept it
        post.setStatus("IN_PROGRESS");
        communityPostRepository.save(post);

        // 2. Create the Task Transaction tracking record
        TaskTransaction transaction = new TaskTransaction();
        transaction.setPost(post);
        transaction.setStatus("ACCEPTED");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        return taskTransactionRepository.save(transaction);
    }

    @Transactional
    public TaskTransaction completeTask(Long postId) {
        // Automatically finds the active task using the Post ID!
        TaskTransaction transaction = taskTransactionRepository.findFirstByPostIdOrderByCreatedAtDesc(postId)
                .orElseThrow(() -> new RuntimeException("Active task not found for this post"));

        transaction.setStatus("COMPLETED");
        transaction.setUpdatedAt(LocalDateTime.now());

        CommunityPost post = transaction.getPost();
        post.setStatus("COMPLETED");
        communityPostRepository.save(post);

        return taskTransactionRepository.save(transaction);
    }

    @Transactional
    public TaskTransaction cancelTask(Long postId) {
        TaskTransaction transaction = taskTransactionRepository.findFirstByPostIdOrderByCreatedAtDesc(postId)
                .orElseThrow(() -> new RuntimeException("Active task not found for this post"));

        transaction.setStatus("CANCELLED");
        transaction.setUpdatedAt(LocalDateTime.now());

        CommunityPost post = transaction.getPost();
        post.setStatus("OPEN");
        communityPostRepository.save(post);

        return taskTransactionRepository.save(transaction);
    }

}