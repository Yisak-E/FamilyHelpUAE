package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.CreatePostDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.UserRepository;
import com.example.familyhelpuae.specification.CommunityPostSpecifications;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final EmailService emailService;

    @Transactional
    @CacheEvict(value = "communityFeed", allEntries = true) // Clear cache so feed updates
    public CommunityPost createTask(CreatePostDto dto, String userEmail) {
        Family family = userRepository.findByEmail(userEmail).orElseThrow().getFamily();

        CommunityPost post = new CommunityPost();
        post.setFamily(family);
        post.setPostType(dto.getPostType()); // "OFFER" or "SEEK"
        post.setTitle(dto.getTitle());
        post.setCategory(dto.getCategory());
        post.setDescription(dto.getDescription());
        post.setUrgency(dto.getUrgency());
        post.setNeededBy(dto.getNeededBy());
        post.setStatus("OPEN");

        return postRepository.save(post);
    }

    /**
     * Level C Security: Uses Specification to build query securely.
     * Caches the result in Redis for high-performance feed scrolling.
     */
    @Cacheable(value = "communityFeed", key = "#type + '-' + #category + '-' + #status")
    public List<CommunityPost> getFilteredTasks(String type, String category, String status) {
        Specification<CommunityPost> spec = CommunityPostSpecifications.withFilters(type, category, status);
        return postRepository.findAll(spec);
    }


    public CommunityPost getTaskById(Long taskId) {
        return postRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Transactional
    @CacheEvict(value = {"communityFeed", "leaderboard"}, allEntries = true)
    public void completeTask(Long taskId, String userEmail) {
        CommunityPost post = postRepository.findById(taskId).orElseThrow();
        Family requestingFamily = userRepository.findByEmail(userEmail).orElseThrow().getFamily();

        // Security check: Only the post creator can mark it as complete
        if (!post.getFamily().getId().equals(requestingFamily.getId())) {
            throw new RuntimeException("Unauthorized: Only the creator can complete this task.");
        }

        post.setStatus("COMPLETED");
        postRepository.save(post);

        // Update Reliability Metrics for the Algorithm
        requestingFamily.setCompletedInteractions(requestingFamily.getCompletedInteractions() + 1);
        familyRepository.save(requestingFamily);

        // Send Email to remind them to leave a review (Feedback/NLP system)
        try {
            emailService.sendTaskCompletionEmail(
                    requestingFamily.getEmail(),
                    post.getTitle(),
                    "your helper" // In a full implementation, you'd fetch the accepted applicant's name here
            );
        } catch (MessagingException e) {
            log.error("Failed to send completion email", e);
        }
    }
}