package com.pi.clique_vagas_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.jobPost.GetJobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.service.JobPostingService;
import com.pi.clique_vagas_api.service.skills.Skill_JobPost_Service;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;

@RestController
@RequestMapping("/job_posting")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private Skill_JobPost_Service skill_JobPost_Service;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @PostMapping
    private ResponseEntity<Long> saveJobPosting(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody JobPostDto postDto) {

        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.save(postDto, company);

        return ResponseEntity.ok(post.getId());
    }

    @PutMapping
    private ResponseEntity<Long> updateJobPosting(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody JobPostWithIdDto postDto) {

        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.update(postDto, company);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("company/{email}")
    private ResponseEntity<List<GetJobPostDto>> getJobPosting(@PathVariable("email") String email) {
        var user = userService.findByEmail(email);
        var company = companyService.getCompanyByUser(user);
        var posts = jobPostingService.findAllPostsByIdCompany(company);
        var completePosts = skill_JobPost_Service.findAllCompletePostsByIdPost(posts);

        return ResponseEntity.ok(completePosts);
    }
}
