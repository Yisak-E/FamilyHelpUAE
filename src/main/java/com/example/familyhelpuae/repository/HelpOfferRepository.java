package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.HelpOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface HelpOfferRepository extends JpaRepository<HelpOffer, Long> {
    HelpOffer getById(Long id);
}