package com.pi.clique_vagas_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.repository.JobPostingRepository;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    public JobPostingModel save(JobPostDto post, CompanyModel company) {

        JobPostingModel jobPosting = new JobPostingModel();
        jobPosting.setCompany(company);
        jobPosting.setTitle(post.title());
        jobPosting.setDescription(post.description());
        jobPosting.setJobPostingStatus(post.jobPostingStatus());
        jobPosting.setAddress(post.address());
        jobPosting.setApplicationDeadline(post.applicationDeadline());
        jobPosting.setPublicationDate(DateUtils.nowInZone());

        return jobPostingRepository.save(jobPosting);
    }

    public JobPostingModel findById(Long id) {
        return jobPostingRepository.findById(id).orElse(null);
    }

    public JobPostingModel findByCompanyId(CompanyModel company) {
        return jobPostingRepository.findByCompany(company)
                .orElseThrow(() -> new RuntimeException("Job Posting not found"));
    }

    public JobPostingModel findByIdAndCompanyId(Long id, CompanyModel company) {
        return jobPostingRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Job Posting not found"));
    }

    public List<JobPostingModel> findAllPostsByIdCompany(CompanyModel company) {
        return jobPostingRepository.findAllByCompany(company);
    }

    public JobPostingModel update(JobPostWithIdDto post, CompanyModel company) {

        JobPostingModel jobPosting = findByIdAndCompanyId(post.id(), company);

        if (post.title() != null)
            jobPosting.setTitle(post.title());
        if (post.description() != null)
            jobPosting.setDescription(post.description());
        if (post.jobPostingStatus() != null)
            jobPosting.setJobPostingStatus(post.jobPostingStatus());
        if (post.address() != null)
            jobPosting.setAddress(post.address());
        jobPosting.setApplicationDeadline(post.applicationDeadline());

        jobPosting.setUpdateAt(DateUtils.nowInZone());

        return jobPostingRepository.save(jobPosting);
    }

    public void delete(JobPostingModel jobPosting) {
        jobPostingRepository.delete(jobPosting);
    }
}
