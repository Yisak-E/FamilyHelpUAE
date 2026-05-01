package com.example.familyhelpuae.service;


import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.specification.CommunityPostSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class HelpService {

    private final CommunityPostRepository postRepository;


    @Cacheable(value = "communityFeed", key = "{#type, #category, #status}")
    public List<CommunityPost> getFilteredPosts(String type, String category, String status) {
        Specification<CommunityPost> spec = CommunityPostSpecifications.withFilters(type, category, status);
        return postRepository.findAll(spec);
    }

    // When a new post is created, we clear the feed cache so users see the new data
    @CacheEvict(value = "communityFeed", allEntries = true)
    public CommunityPost createPost(CreatePostDto dto, String email) {
        // ... logic ...
        return null;
    }

    public List<CommunityPost> getPosts(String type) {
        return null;
    }

    public List<CommunityPost> getMyPosts(String name) {
        return null;
    }
}