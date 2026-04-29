package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.UserRepository; // Assuming you use this to get the Family
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Fetches posts based on the type requested from the frontend.
     */
    public List<CommunityPost> getPosts(String type) {
        if (type == null || type.isBlank() || type.equalsIgnoreCase("ALL")) {
            return postRepository.findAllByOrderByCreatedAtDesc();
        }
        return postRepository.findByPostTypeOrderByCreatedAtDesc(type.toUpperCase());
    }

    /**
     * Creates a new Community Post (either OFFER or SEEK)
     */
    public CommunityPost createPost(CreatePostDto dto, String userEmail) {
        // Find the user making the request to attach their Family
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Family family = user.getFamily();

        CommunityPost post = new CommunityPost();
        post.setFamily(family);
        post.setPostType(dto.getPostType().toUpperCase());
        post.setTitle(dto.getTitle());
        post.setCategory(dto.getCategory().toLowerCase());
        post.setDescription(dto.getDescription());

        // Handle specific fields based on the type
        if ("SEEK".equalsIgnoreCase(post.getPostType())) {
            post.setUrgency(dto.getUrgency());
            post.setNeededBy(dto.getNeededBy());
        } else if ("OFFER".equalsIgnoreCase(post.getPostType())) {
            post.setAvailability(dto.getAvailability());
        }

        return postRepository.save(post);
    }

    /**
     * Fetches "My Activities" for the logged-in user
     */
    public List<CommunityPost> getMyPosts(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByFamilyIdOrderByCreatedAtDesc(user.getFamily().getId());
    }
}