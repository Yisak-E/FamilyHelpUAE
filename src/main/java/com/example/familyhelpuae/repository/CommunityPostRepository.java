package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    // Finds everything, sorted newest first (for the "Everything" tab)
    List<CommunityPost> findAllByOrderByCreatedAtDesc();

    // Finds specific types (OFFER or SEEK), sorted newest first
    List<CommunityPost> findByPostTypeOrderByCreatedAtDesc(String postType);

    // Finds posts for a specific family (for "My Activities" and Profiles)
    List<CommunityPost> findByFamilyIdOrderByCreatedAtDesc(Long familyId);
}