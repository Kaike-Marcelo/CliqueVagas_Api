package com.pi.clique_vagas_api.controller.jobPosting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.jobPost.GetAllJobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.GetJobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.service.jobPost.JobPostingService;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.skills.Skill_JobPost_Service;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/job_posting")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private Skill_JobPost_Service skill_JobPost_Service;

    @Autowired
    private Skill_Intern_Service skill_Intern_Service;

    @Autowired
    private UserService userService;

    @Autowired
    private InternService internService;

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Long> saveJobPosting(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody JobPostDto postDto) {

        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.save(postDto, company);

        return ResponseEntity.ok(post.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updateJobPosting(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody JobPostWithIdDto postDto) {

        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.update(postDto, company);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("/public")
    public ResponseEntity<List<GetAllJobPostDto>> getPublicJobPosts() {
        List<GetAllJobPostDto> posts = jobPostingService.findAllActivePostsDto();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/public/intern")
    public ResponseEntity<List<GetAllJobPostDto>> getJobPostsForIntern(
            @AuthenticationPrincipal UserDetails userDetails) {

        UserModel user = userService.findByEmail(userDetails.getUsername());
        InternModel intern = internService.getInternByUser(user);
        List<Skill_Intern_Model> internSkills = skill_Intern_Service.getSkillsFromInternByUserId(intern);
        List<GetAllJobPostDto> posts = jobPostingService.getJobPostsForInternDto(internSkills);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/company")
    public ResponseEntity<List<GetAllJobPostDto>> getJobPostsForCompany(
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        List<GetAllJobPostDto> posts = jobPostingService.findAllJobPostsByIdCompanyDto(company);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/company/{jobPostingId")
    public ResponseEntity<JobPostingModel> getJobPostingById(@PathVariable Long jobPostingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.findByIdAndCompanyId(jobPostingId, company);
        return ResponseEntity.ok(post);
    }

    @GetMapping("company/{email}")
    public ResponseEntity<List<GetJobPostDto>> getJobPosting(@PathVariable("email") String email) {
        var user = userService.findByEmail(email);
        var company = companyService.getCompanyByUser(user);
        var posts = jobPostingService.findAllPostsByIdCompany(company);
        var completePosts = skill_JobPost_Service.findAllCompletePostsByIdPost(posts);

        return ResponseEntity.ok(completePosts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        jobPostingService.delete(id, company);
        return ResponseEntity.noContent().build();
    }
}
