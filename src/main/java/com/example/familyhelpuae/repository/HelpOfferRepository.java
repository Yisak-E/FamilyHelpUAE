package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.HelpOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Set;

@Repository
public interface HelpOfferRepository extends JpaRepository<HelpOffer, Long> {
    HelpOffer getById(Long id);

    boolean existsByProviderFamilyAndServiceCategoryAndStatus(String providerFamilyId, String serviceCategory,  String Status);

    List<HelpOffer> findByProviderFamily(Family family);

    List<HelpOffer> findByHelpRequest_Id(Set<Long> singleton);
}