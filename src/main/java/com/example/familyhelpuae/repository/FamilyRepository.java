package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family,Long> {
    Optional<Family> findByFamilyName(String family_name);
}
