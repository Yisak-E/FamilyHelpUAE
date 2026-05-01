package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.PostApplication;
import com.example.familyhelpuae.model.*;
import com.example.familyhelpuae.repository.CommunityPostRepository;
import com.example.familyhelpuae.repository.PostApplicationRepository;
import com.example.familyhelpuae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final PostApplicationRepository applicationRepository;
    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService; // For FCM/Email alerts

    @Transactional
    public PostApplication applyToPost(Long postId, String email) {
        CommunityPost post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Family applicant = user.getFamily();

        // Security check: Can't apply to your own post
        if (post.getFamily().getId().equals(applicant.getId())) {
            throw new RuntimeException("You cannot apply to your own post.");
        }

        // Logic Check: Prevent double applications
        if (applicationRepository.existsByPostAndApplicantFamily(post, applicant)) {
            throw new RuntimeException("Already applied.");
        }

        PostApplication app = new PostApplication();
        app.setPost(post);
        app.setApplicantFamily(applicant);
        app.setStatus("PENDING");

        // Update Post Metadata
        post.setApplicationCount(post.getApplicationCount() + 1);
        postRepository.save(post);

        // TRIGGER ADVANCED FEATURE: Push Notification
        notificationService.sendFCMNotification(
            post.getFamily().getId(), 
            "New Applicant!", 
            applicant.getFamilyName() + " wants to help with: " + post.getTitle()
        );

        return applicationRepository.save(app);
    }

    public List<PostApplication> getApplicantsForPost(Long postId, String ownerEmail) {
        CommunityPost post = postRepository.findById(postId).orElseThrow();
        // Verify ownership
        if (!post.getFamily().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Access denied.");
        }
        return applicationRepository.findByPostOrderByApplicantFamilyTrustScoreDesc(post);
    }

    @Transactional
    public PostApplication acceptApplication(Long applicationId, String ownerEmail) {
        PostApplication selectedApp = applicationRepository.findById(applicationId).orElseThrow();
        CommunityPost post = selectedApp.getPost();

        if (!post.getFamily().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        // 1. Update Selected Application
        selectedApp.setStatus("ACCEPTED");
        selectedApp.setSelectionDate(LocalDateTime.now());

        // 2. Update Post Lifecycle
        post.setStatus("IN_PROGRESS");
        postRepository.save(post);

        // 3. REJECT ALL OTHERS: Level C logic for consistency
        List<PostApplication> others = applicationRepository.findByPostAndIdNot(post, applicationId);
        others.forEach(other -> other.setStatus("REJECTED"));
        applicationRepository.saveAll(others);

        // TRIGGER ADVANCED FEATURE: Email Confirmation via SMTP
        notificationService.sendConfirmationEmail(selectedApp);

        return applicationRepository.save(selectedApp);
    }

    public void cancelApplication(Long applicationId, String name) {
    }
}