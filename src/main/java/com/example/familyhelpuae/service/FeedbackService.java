package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.CreateFeedbackDto;
import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.FeedbackRepository;
import com.example.familyhelpuae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Feedback submitFeedback(CreateFeedbackDto dto, String reviewerEmail) {
        // 1. Get the Post
        CommunityPost post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 2. Get the Reviewer Family (Logged in user)
        User reviewerUser = userRepository.findByEmail(reviewerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Family reviewerFamily = reviewerUser.getFamily();

        // 3. Identify the Reviewed Family
        // If the reviewer is the one who created the post, they are reviewing the helper.
        // Otherwise, they are reviewing the post creator.
        Family reviewedFamily = post.getFamily().equals(reviewerFamily)
                ? identifyHelperFamily(post) // Logic to find who accepted the task
                : post.getFamily();

        Feedback feedback = new Feedback();
        feedback.setPost(post);
        feedback.setReviewerFamily(reviewerFamily);
        feedback.setReviewedFamily(reviewedFamily);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        feedback.setCreatedAt(LocalDateTime.now());

        // 4. Update Family Reputation Score
        updateFamilyReputation(reviewedFamily, dto.getRating());

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackForFamily(Long familyId) {
        return feedbackRepository.findByReviewedFamilyIdOrderByCreatedAtDesc(familyId);
    }

    private void updateFamilyReputation(Family family, int newRating) {
        double currentScore = family.getReputationScore();
        int totalInteractions = family.getCompletedInteractions();

        // Simple moving average for reputation
        double newScore = ((currentScore * totalInteractions) + newRating) / (totalInteractions + 1);

        family.setReputationScore(newScore);
        family.setCompletedInteractions(totalInteractions + 1);
        // familyRepository.save(family); // Ensure familyRepo is injected if needed
    }

    private Family identifyHelperFamily(CommunityPost post) {
        // Implementation depends on how you track who accepted the task in TaskTransaction
        // For now, returning a placeholder or finding the transaction
        return null;
    }
}