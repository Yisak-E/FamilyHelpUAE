package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long>, JpaSpecificationExecutor<CommunityPost> {
    // JpaSpecificationExecutor allows for the Level C SQL Injection protection via Criteria API
}