package com.pi.clique_vagas_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.jobPost.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.enums.Status;

@Repository
public interface InscriptionsJobPostingRepository extends JpaRepository<InscriptionsJobPostingModel, Long> {

    List<InscriptionsJobPostingModel> findAllByJobPostingId(JobPostingModel jobPostingModel);

    @Query("SELECT i FROM InscriptionsJobPostingModel i WHERE i.userId = :userId AND i.jobPostingId.jobPostingStatus = :status")
    List<InscriptionsJobPostingModel> findAllByUserIdAndJobPostingStatus(
            @Param("userId") UserModel userId,
            @Param("status") Status status);

}
