package com.example.familyhelpuae.repository;

import com.example.familyhelpuae.model.CommunityPost;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.PostApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {

    List<PostApplication> findByPostOrderByApplicantFamilyTrustScoreDesc(CommunityPost post);

    @Query("SELECT pa FROM PostApplication pa WHERE pa.status = 'ACCEPTED' AND " +
            "(pa.post.family.id = :familyId OR pa.applicantFamily.id = :familyId)")
    List<PostApplication> findAllActiveInteractions(@Param("familyId") Long familyId);

    List<PostApplication> findByPostAndIdNot(CommunityPost post, Long applicationId);

    boolean existsByPostAndApplicantFamily(CommunityPost post, Family applicant);
}