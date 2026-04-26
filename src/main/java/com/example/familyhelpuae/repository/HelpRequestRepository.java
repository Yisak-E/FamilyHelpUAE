package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.HelpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {
    // find requests by requester family
    List<HelpRequest> findByRequesterFamily(Family family);

    // check duplicate request by requester family name, category and status
    boolean existsByRequesterFamily_FamilyNameAndCategoryAndStatus(String familyName, String category, String status);

    Optional<HelpRequest> findById(Long id);
}