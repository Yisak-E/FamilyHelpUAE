package com.example.familyhelpuae.repository;
import com.example.familyhelpuae.model.SupportTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Repository

public interface SupportTaskRepository extends JpaRepository<SupportTask, Long> {
    // find all support tasks for a given help request id
    List<SupportTask> findByRequest_Id(Long requestId);
}