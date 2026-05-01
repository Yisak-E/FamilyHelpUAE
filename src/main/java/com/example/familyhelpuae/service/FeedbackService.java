package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.FeedbackRepository;
import com.example.familyhelpuae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;
    private final TrustScoreService trustScoreService;

    /**
     * Processes new feedback: Analyzes sentiment, saves record, and updates Trust Score.
     */
    @Transactional
    public void processFeedback(Long postId, String reviewerEmail, String comment, int rating) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Family reviewer = userRepository.findByEmail(reviewerEmail).orElseThrow().getFamily();

        // Identify the family being reviewed (if reviewer is creator, reviewed is the applicant, and vice-versa)
        Family reviewedFamily = post.getFamily().equals(reviewer) ?
                /* Logic to find the accepted applicant */ null : post.getFamily();

        // 1. Analyze Sentiment using Stanford CoreNLP
        double sentimentScore = trustScoreService.analyzeSentiment(comment);

        // 2. Save Feedback Record
        Feedback feedback = new Feedback();
        feedback.setPost(post);
        feedback.setReviewerFamily(reviewer);
        feedback.setReviewedFamily(reviewedFamily);
        feedback.setComment(comment);
        feedback.setNumericalRating(rating);
        feedback.setSentimentScore(sentimentScore);

        feedbackRepository.save(feedback);

        // 3. Trigger Trust Score Recalculation (Updates DB and Evicts Redis Cache)
        trustScoreService.updateFamilyTrustScore(reviewedFamily.getId());
    }
}