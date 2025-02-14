package com.pi.clique_vagas_api.service.jobPost;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.repository.jobPosting.JobPostingRepository;
import com.pi.clique_vagas_api.repository.jobPosting.LikeRepository;
import com.pi.clique_vagas_api.resources.dto.jobPost.GetAllJobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.resources.enums.Status;
import com.pi.clique_vagas_api.service.skills.SkillCompatibilityService;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

import jakarta.transaction.Transactional;

@Service
public class JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private SkillCompatibilityService skillCompatibilityService;

    @Autowired
    private LikeRepository likeJobPostingRepository;

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

    public List<GetAllJobPostDto> findAllJobPostsByIdCompanyDto(CompanyModel company) {
        return findAllPostsByIdCompany(company).stream()
                .map(post -> new GetAllJobPostDto(
                        new JobPostWithIdDto(post.getId(), post.getTitle(), post.getDescription(),
                                post.getJobPostingStatus(), post.getAddress(), post.getApplicationDeadline()),
                        post.getCompany().getId(), post.getCompany().getUserId().getEmail(),
                        post.getCompany().getCompanyName(),
                        FileUtils.loadFileFromPath(post.getCompany().getUserId().getUrlImageProfile())))
                .collect(Collectors.toList());
    }

    public List<JobPostingModel> findAllActivePosts() {
        return jobPostingRepository.findAllByJobPostingStatusNot(Status.INACTIVE);
    }

    public List<GetAllJobPostDto> findAllActivePostsDto() {
        return findAllActivePosts().stream()
                .map(post -> new GetAllJobPostDto(
                        new JobPostWithIdDto(post.getId(), post.getTitle(), post.getDescription(),
                                post.getJobPostingStatus(), post.getAddress(), post.getApplicationDeadline()),
                        post.getCompany().getId(), post.getCompany().getUserId().getEmail(),
                        post.getCompany().getCompanyName(),
                        FileUtils.loadFileFromPath(post.getCompany().getUserId().getUrlImageProfile())))
                .collect(Collectors.toList());
    }

    public List<JobPostingModel> getJobPostsForIntern(List<Skill_Intern_Model> internSkills) {
        List<JobPostingModel> activePosts = findAllActivePosts();

        return activePosts.stream()
                .sorted(Comparator.comparingInt(
                        post -> -skillCompatibilityService.calculateCompatibilityScore(internSkills, post)))
                .collect(Collectors.toList());
    }

    public List<GetAllJobPostDto> getJobPostsForInternDto(List<Skill_Intern_Model> internSkills) {
        return getJobPostsForIntern(internSkills).stream()
                .map(post -> new GetAllJobPostDto(
                        new JobPostWithIdDto(post.getId(), post.getTitle(), post.getDescription(),
                                post.getJobPostingStatus(), post.getAddress(), post.getApplicationDeadline()),
                        post.getCompany().getId(), post.getCompany().getUserId().getEmail(),
                        post.getCompany().getCompanyName(),
                        FileUtils.loadFileFromPath(post.getCompany().getUserId().getUrlImageProfile())))
                .collect(Collectors.toList());
    }

    public JobPostingModel update(JobPostWithIdDto post, CompanyModel company) {

        JobPostingModel jobPosting = findByIdAndCompanyId(post.id(), company);

        if (post.title() != null)
            jobPosting.setTitle(post.title());
        if (post.description() != null)
            jobPosting.setDescription(post.description());
        if (post.jobPostingStatus() != null && jobPosting.getApplicationDeadline().isBefore(DateUtils.nowInZone()))
            jobPosting.setJobPostingStatus(post.jobPostingStatus());
        if (post.address() != null)
            jobPosting.setAddress(post.address());
        if (post.applicationDeadline() != null)
            jobPosting.setApplicationDeadline(post.applicationDeadline());

        jobPosting.setUpdateAt(DateUtils.nowInZone());

        return jobPostingRepository.save(jobPosting);
    }

    public void delete(Long idPost, CompanyModel companyModel) {
        JobPostingModel jobPosting = findByIdAndCompanyId(idPost, companyModel);
        likeJobPostingRepository.deleteAllByJobPosting(jobPosting);
        jobPostingRepository.delete(jobPosting);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredJobPostings() {

        ZonedDateTime now = DateUtils.nowInZone();
        List<JobPostingModel> activePosts = jobPostingRepository
                .findAllByJobPostingStatusNot(Status.INACTIVE);

        activePosts.stream()
                .filter(post -> post.getApplicationDeadline().isBefore(now))
                .forEach(post -> {
                    post.setJobPostingStatus(Status.INACTIVE);
                    post.setUpdateAt(now);
                    jobPostingRepository.save(post);
                });
    }
}
