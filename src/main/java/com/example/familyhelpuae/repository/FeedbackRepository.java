package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.SupportTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository

interface FeedbackRepository extends JpaRepository<SupportTask,Long> {

}