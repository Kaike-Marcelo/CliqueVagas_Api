package com.pi.clique_vagas_api.controller.skills;

import java.net.URI;
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

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.jobPost.Skill_JobPost_Dto;
import com.pi.clique_vagas_api.service.JobPostingService;
import com.pi.clique_vagas_api.service.skills.SkillService;
import com.pi.clique_vagas_api.service.skills.Skill_JobPost_Service;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;

@RestController
@RequestMapping("/skill_posting")
public class Skill_JobPosting_Controller {

    @Autowired
    private Skill_JobPost_Service skillJobPostService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobPostingService jobPostService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Long> addSkillJobPost(@RequestBody Skill_JobPost_Dto body,
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var jobPost = jobPostService.findByIdAndCompanyId(body.getIdJobPost(), company);
        var skill = skillService.getSkillById(body.getIdSkill());

        var skillJobPostId = skillJobPostService.createSkillPost(skill, jobPost,
                body);

        return ResponseEntity.created(URI.create("/skill/" +
                skillJobPostId.getId())).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSkillJobPost(@RequestBody Skill_Intermediate_WithIdDto body,
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var JobPost = jobPostService.findByCompanyId(company);

        skillJobPostService.updateSkillPost(body, JobPost);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Skill_Intermediate_WithIdDto>> getAllSkillsPostByIdCompany(
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var JobPost = jobPostService.findByCompanyId(company);

        var skillsJobPost = skillJobPostService.getSkillsDtoByPostId(JobPost);
        return ResponseEntity.ok(skillsJobPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkillJobPost(@PathVariable Long id) {
        skillJobPostService.deleteSkillPost(id);
        return ResponseEntity.ok().build();
    }
}
