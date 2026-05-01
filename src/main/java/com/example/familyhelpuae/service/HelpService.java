package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.UserRepository;
import com.example.familyhelpuae.specification.CommunityPostSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository; // Injected to find the logged-in user

    @Cacheable(value = "communityFeed", key = "{#type, #category, #status}")
    public List<CommunityPost> getFilteredPosts(String type, String category, String status) {
        Specification<CommunityPost> spec = CommunityPostSpecifications.withFilters(type, category, status);
        return postRepository.findAll(spec);
    }

    // When a new post is created, we clear the feed cache so users see the new data
    @CacheEvict(value = "communityFeed", allEntries = true)
    public CommunityPost createPost(CreatePostDto dto, String email) {
        // 1. Find the user making the request
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get their associated family
        Family family = user.getFamily();
        if (family == null) {
            throw new RuntimeException("User does not belong to a registered family");
        }

        // 3. Map DTO to Entity
        CommunityPost post = new CommunityPost();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setCategory(dto.getCategory());
        post.setPostType(dto.getPostType()); // e.g., SEEK or OFFER
        post.setUrgency(dto.getUrgency());
        post.setNeededBy(dto.getNeededBy());

        // 4. Set system defaults
        post.setStatus("OPEN");
        post.setCreatedAt(LocalDateTime.now());
        post.setFamily(family);

        // 5. Save and return
        return postRepository.save(post);
    }

    public List<CommunityPost> getPosts(String type) {
        // If a type is provided (SEEK/OFFER), filter by it. Otherwise, return all OPEN posts.
        return getFilteredPosts(type, null, "OPEN");
    }

    public List<CommunityPost> getMyPosts(String email) {
        // Find the user to get their family ID
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return all posts created by this family
        // Note: You will need to add findByFamilyIdOrderByCreatedAtDesc in your repository interface
        return postRepository.findByFamilyIdOrderByCreatedAtDesc(user.getFamily().getId());
    }

    // Bonus: The method to mark a task as completed (closes the loop!)
    @CacheEvict(value = "communityFeed", allEntries = true)
    public CommunityPost completeTask(Long postId, String email) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Security check: Only the family who created the post can mark it as complete
        User user = userRepository.findByEmail(email).orElseThrow();
        if (!post.getFamily().getId().equals(user.getFamily().getId())) {
            throw new RuntimeException("Not authorized to complete this task");
        }

        post.setStatus("COMPLETED");
        return postRepository.save(post);
    }
}