package com.pi.clique_vagas_api.repository.jobPosting;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.jobPost.LikeJobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeJobPostingModel, Long> {
    Optional<LikeJobPostingModel> findByJobPostingAndUser(JobPostingModel jobPosting, UserModel user);

    Long countByJobPosting(JobPostingModel jobPosting);

    boolean existsByJobPostingAndUser(JobPostingModel jobPosting, UserModel user);

    @Transactional
    void deleteAllByJobPosting(JobPostingModel jobPostingModel);

}