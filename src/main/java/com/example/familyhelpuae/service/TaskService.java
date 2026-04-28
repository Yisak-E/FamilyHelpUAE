package com.example.familyhelpuae.service;

import com.example.familyhelpuae.model.HelpOffer;
import com.example.familyhelpuae.model.HelpRequest;
import com.example.familyhelpuae.model.SupportTask;
import com.example.familyhelpuae.repository.HelpOfferRepository;
import com.example.familyhelpuae.repository.HelpRequestRepository;
import com.example.familyhelpuae.repository.SupportTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final SupportTaskRepository supportTaskRepo;
    private final HelpOfferRepository helpOfferRepo;
    private final HelpRequestRepository helpRequestRepo;

    public TaskService(SupportTaskRepository supportTaskRepo,
                       HelpOfferRepository helpOfferRepo,
                       HelpRequestRepository helpRequestRepo) {
        this.supportTaskRepo = supportTaskRepo;
        this.helpOfferRepo = helpOfferRepo;
        this.helpRequestRepo = helpRequestRepo;
    }

    /**
     * Accepts a help request and links it to a help offer.
     * This effectively "answers" a request from another family.
     */
    @Transactional
    public SupportTask acceptTask(String helpRequestId, Long helpOfferId) {
        // 1. Find the Help Request (The family needing help)
        HelpRequest helpRequest = helpRequestRepo.findById(Long.parseLong(helpRequestId))
                .orElseThrow(() -> new RuntimeException("Help Request not found: " + helpRequestId));

        // 2. Find the Help Offer (The family providing help)
        HelpOffer helpOffer = helpOfferRepo.findById(helpOfferId)
                .orElseThrow(() -> new RuntimeException("Help Offer not found: " + helpOfferId));

        // 3. Logic Check: Ensure request is still "Open"
        if (!helpRequest.getStatus().equalsIgnoreCase("open")) {
            throw new RuntimeException("This help request is no longer available.");
        }

        // 4. Update the Request Status
        helpRequest.setStatus("ACCEPTED");
        helpRequestRepo.save(helpRequest);

        // 5. Create and link the SupportTask based on your model
        SupportTask task = new SupportTask();
        task.setRequest(helpRequest); // Set the @ManyToOne link to Request
        task.setOffer(helpOffer);     // Set the @ManyToOne link to Offer
        task.setStatus("IN_PROGRESS");
        task.setCreatedAt(LocalDateTime.now());

        return supportTaskRepo.save(task);
    }

    /**
     * Completes the task and closes the request.
     */
    @Transactional
    public SupportTask completeTask(String taskId) {
        SupportTask task = supportTaskRepo.findById(Long.parseLong(taskId))
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        task.setStatus("COMPLETED");

        // Mark the linked request as closed
        if (task.getRequest() != null) {
            task.getRequest().setStatus("CLOSED");
            helpRequestRepo.save(task.getRequest());
        }

        return supportTaskRepo.save(task);
    }

    /**
     * Cancels the task and re-opens the request for others.
     */
    @Transactional
    public SupportTask cancelTask(String taskId) {
        SupportTask task = supportTaskRepo.findById(Long.parseLong(taskId))
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        task.setStatus("CANCELLED");

        // Re-open the request
        if (task.getRequest() != null) {
            task.getRequest().setStatus("OPEN");
            helpRequestRepo.save(task.getRequest());
        }

        return supportTaskRepo.save(task);
    }
}