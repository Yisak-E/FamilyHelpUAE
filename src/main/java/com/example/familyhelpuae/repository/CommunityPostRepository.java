package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long>, JpaSpecificationExecutor<CommunityPost> {

    // Finds posts by family ID and sorts them newest first
    List<CommunityPost> findByFamilyIdOrderByCreatedAtDesc(Long familyId);

}