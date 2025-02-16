package com.pi.clique_vagas_api.repository.jobPosting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT DISTINCT j FROM JobPostingModel j LEFT JOIN j.company c WHERE " +
            "(LOWER(j.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(j.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.companyName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<JobPostingModel> searchActivePosts(@Param("searchTerm") String searchTerm);

}
