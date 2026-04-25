package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

}