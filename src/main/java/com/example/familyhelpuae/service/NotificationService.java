package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.PostApplication;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    /**
     * Sends a Real-time Push Notification via Firebase (FCM).
     * Used for: "New Applicant", "Application Accepted", "Urgent Request Nearby".
     */
    public void sendFCMNotification(Long targetFamilyId, String title, String body) {
        // In a real implementation, you fetch the FCM token from the database
        // mapped to the targetFamilyId.
        String dummyToken = "fcm_token_from_db"; 

        Message message = Message.builder()
                .setToken(dummyToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            log.info("Push notification sent to family: {}", targetFamilyId);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM message", e);
        }
    }

    /**
     * Sends a formal SMTP Email.
     * Used for: "Task Confirmation", "Reputation Update", "Subscription Alerts".
     */
    public void sendConfirmationEmail(PostApplication application) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("community@familyhelpuae.com");
        message.setTo(application.getApplicantFamily().getEmail());
        
        message.setSubject("Application Accepted: " + application.getPost().getTitle());
        message.setText("Congratulations! Your application to help with '" + 
                        application.getPost().getTitle() + "' has been accepted by " + 
                        application.getPost().getFamily().getFamilyName() + ".\n\n" +
                        "Please check your dashboard calendar for schedule details.");

        try {
            mailSender.send(message);
            log.info("Confirmation email sent to: {}", application.getApplicantFamily().getEmail());
        } catch (Exception e) {
            log.error("SMTP Error: Failed to send email", e);
        }
    }
}