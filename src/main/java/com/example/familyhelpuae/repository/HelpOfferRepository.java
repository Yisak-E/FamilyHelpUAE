package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.HelpOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface HelpOfferRepository extends JpaRepository<HelpOffer, Long> {
    // check by provider family name + category + status
    boolean existsByProviderFamily_FamilyNameAndServiceCategoryAndStatus(String familyName, String serviceCategory, String status);

    // find offers by provider family
    List<HelpOffer> findByProviderFamily(Family family);
}