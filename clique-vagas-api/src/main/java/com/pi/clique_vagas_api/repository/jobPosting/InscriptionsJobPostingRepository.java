package com.pi.clique_vagas_api.repository.jobPosting;

import java.util.List;
import java.util.Optional;

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

    Optional<InscriptionsJobPostingModel> findByUserId(UserModel user, JobPostingModel jobPostingModel);

    List<InscriptionsJobPostingModel> findAllByJobPostingId(JobPostingModel jobPostingModel);

    @Query("SELECT i FROM InscriptionsJobPostingModel i WHERE i.userId = :userId AND i.jobPostingId.jobPostingStatus = :status")
    List<InscriptionsJobPostingModel> findAllByUserIdAndJobPostingStatus(
            @Param("userId") UserModel userId,
            @Param("status") Status status);

    List<InscriptionsJobPostingModel> findAllByUserIdAndJobPostingId(UserModel userId, JobPostingModel jobPostingModel);

    Boolean deleteByUserIdAndId(UserModel userId, Long id);

}
