package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.HelpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {
    boolean existsByProviderFamilyAndServiceCategoryAndStatus(String requesterFamilyId, String category, String Status);

    List<HelpRequest> findByRequesterFamily(Family family);
}