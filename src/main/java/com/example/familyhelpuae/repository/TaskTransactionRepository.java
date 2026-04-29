package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.TaskTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskTransactionRepository extends JpaRepository<TaskTransaction, Long> {

    // Grabs the most recent task associated with this specific Post
    Optional<TaskTransaction> findFirstByPostIdOrderByCreatedAtDesc(Long postId);
}