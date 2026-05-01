package com.example.familyhelpuae.service;


import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.Feedback;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.FeedbackRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrustScoreService {

    private final FamilyRepository familyRepository;
    private final FeedbackRepository feedbackRepository;

    private static final StanfordCoreNLP pipeline;

    // Static initializer for the heavy NLP models
    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,parse,sentiment");
        pipeline = new StanfordCoreNLP(props);
        log.info("Stanford CoreNLP Pipeline initialized successfully.");
    }

    /**
     * Analyzes text sentiment using Stanford CoreNLP RNN.
     * Returns a score between 1.0 (Very Negative) and 10.0 (Very Positive).
     */
    public double analyzeSentiment(String text) {
        if (text == null || text.isBlank()) return 5.0; // Neutral baseline

        Annotation doc = new Annotation(text);
        pipeline.annotate(doc);

        double totalScore = 0;
        int sentenceCount = 0;

        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            // RNN Predicted Class: 0=Very Neg, 1=Neg, 2=Neutral, 3=Pos, 4=Very Pos
            int sentimentInt = RNNCoreAnnotations.getPredictedClass(
                    sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class)
            );
            totalScore += sentimentInt;
            sentenceCount++;
        }

        double averageRnnScore = (sentenceCount > 0) ? (totalScore / sentenceCount) : 2.0;

        // Convert 0-4 scale to 1-10 scale
        // (score / 4) * 9 + 1
        return (averageRnnScore / 4.0) * 9.0 + 1.0;
    }

    /**
     * Recalculates the Trust Score and updates Redis Cache.
     * Weights: Sentiment (50%), Reliability (30%), Engagement (20%).
     */
    @Transactional
    @CacheEvict(value = "familyTrustScores", key = "#familyId")
    public void updateFamilyTrustScore(Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("Family not found"));

        List<Feedback> feedbacks = feedbackRepository.findByReviewedFamilyIdOrderByCreatedAtDesc(familyId);

        // 1. Sentiment Component (50%)
        double avgSentiment = feedbacks.stream()
                .mapToDouble(Feedback::getSentimentScore)
                .average()
                .orElse(5.0); // Default for new families

        // 2. Reliability Component (30%)
        // Formula: (Completed / (Completed + Cancelled)) * 10
        int completed = family.getCompletedInteractions();
        int cancelled = family.getCancelledInteractions();
        int totalAttempted = completed + cancelled;
        double reliability = (totalAttempted > 0)
                ? ((double) completed / totalAttempted) * 10.0
                : 7.0; // Benefit of the doubt for new users

        // 3. Engagement Component (20%)
        // Logarithmic scaling: Rewards high activity but plateaus to prevent spamming
        double engagement = Math.min(10.0, 3.0 * Math.log10(completed + 1.0));

        // Weighted Average
        double finalTrustScore = (avgSentiment * 0.5) + (reliability * 0.3) + (engagement * 0.2);

        // Round to 1 decimal place (e.g., 8.7)
        double roundedScore = Math.round(finalTrustScore * 10.0) / 10.0;

        family.setTrustScore(roundedScore);
        familyRepository.save(family);

        log.info("Updated Trust Score for Family {}: {}", familyId, roundedScore);
    }

    /**
     * Fetches the Trust Score, prioritizing the Redis Cache.
     */
    @Cacheable(value = "familyTrustScores", key = "#familyId")
    public double getTrustScore(Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("Family not found"));
        return family.getTrustScore();
    }
}