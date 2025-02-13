package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;
import com.pi.clique_vagas_api.repository.skills.Skill_JobPost_Repository;

public class SkillCompatibilityService {

    @Autowired
    private Skill_JobPost_Repository skillJobPostRepository;

    public int calculateCompatibilityScore(List<Skill_Intern_Model> internSkills, JobPostingModel post) {
        List<Skill_JobPosting_Model> postSkills = skillJobPostRepository.findAllByIdJobPosting(post);
        return (int) internSkills.stream()
                .filter(internSkill -> postSkills.stream()
                        .anyMatch(postSkill -> postSkill.getIdSkill().equals(internSkill.getIdSkill())))
                .count();
    }

}
