package com.pi.clique_vagas_api.repository.jobPosting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.resources.enums.Status;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingModel, Long> {

    Optional<JobPostingModel> findByIdAndCompany(Long id, CompanyModel companyId);

    Optional<JobPostingModel> findByCompany(CompanyModel companyId);

    List<JobPostingModel> findAllByCompany(CompanyModel companyId);

    List<JobPostingModel> findAllByJobPostingStatusNot(Status status);

}
