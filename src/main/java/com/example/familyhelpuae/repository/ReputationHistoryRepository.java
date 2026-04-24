package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.ReputationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
interface ReputationHistoryRepository extends JpaRepository<ReputationHistory, Long> {

}