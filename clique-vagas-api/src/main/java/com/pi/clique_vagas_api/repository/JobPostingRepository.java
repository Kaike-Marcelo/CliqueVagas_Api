package com.pi.clique_vagas_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingModel, Long> {

    Optional<JobPostingModel> findByIdAndCompanyId(Long id, CompanyModel companyId);

    Optional<JobPostingModel> findByCompanyId(CompanyModel companyId);

    List<JobPostingModel> findAllByCompanyId(CompanyModel companyId);

}
