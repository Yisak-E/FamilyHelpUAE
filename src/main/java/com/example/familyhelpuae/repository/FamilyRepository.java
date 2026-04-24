package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface FamilyRepository extends JpaRepository<Family,Long> {

}
