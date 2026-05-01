package com.example.familyhelpuae.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends the initial account verification email.
     */
    public void sendVerificationEmail(String toEmail, String token) throws MessagingException {
        String verificationUrl = "https://localhost:3000/verify-email?token=" + token;
        
        String htmlContent = "<h1>Welcome to FamilyHelpUAE!</h1>" +
                "<p>Please click the button below to verify your account and start connecting with your community.</p>" +
                "<a href='" + verificationUrl + "' style='background-color: #2563eb; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Verify Account</a>" +
                "<p>If the button doesn't work, copy this link: " + verificationUrl + "</p>";

        sendHtmlEmail(toEmail, "Verify Your FamilyHelpUAE Account", htmlContent);
    }

    /**
     * Sends a notification when a task is completed.
     */
    public void sendTaskCompletionEmail(String toEmail, String postTitle, String otherFamilyName) throws MessagingException {
        String htmlContent = "<h2>Task Completed!</h2>" +
                "<p>Great news! The task <strong>" + postTitle + "</strong> has been marked as completed.</p>" +
                "<p>Don't forget to leave feedback for <strong>" + otherFamilyName + "</strong> to help build community trust!</p>";

        sendHtmlEmail(toEmail, "Update: Task Completed", htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("no-reply@familyhelpuae.com");
        helper.setText(content, true); // Set to true for HTML

        mailSender.send(message);
    }
}