package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.HelpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {
}