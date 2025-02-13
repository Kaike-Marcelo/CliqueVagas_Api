package com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost;

import org.springframework.beans.factory.annotation.Autowired;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.skills.Skill_JobPost_Service;

public class PontuationService {

    @Autowired
    private Skill_Intern_Service skill_Intern_Service;

    @Autowired
    private Skill_JobPost_Service skill_JobPost_Service;

    public Double calculatePontuation(InternModel intern, JobPostingModel jobPost) {

        var skillInterns = skill_Intern_Service.getSkillsDtoByInternId(intern);
        var skillJobPosts = skill_JobPost_Service.getSkillsDtoByPostId(jobPost);

        Double totalCompatibilityScore = 0.0;
        int matchingSkillsCount = 0;

        for (Skill_Intermediate_WithIdDto skillPost : skillJobPosts) {
            for (Skill_Intermediate_WithIdDto skillIntern : skillInterns) {
                if (skillPost.idSkill().equals(skillIntern.idSkill())) {
                    totalCompatibilityScore += skillPost.proficiencyLevel()
                            .calculateSkillCompatibility(skillIntern.proficiencyLevel());
                    matchingSkillsCount++;
                }
            }
        }

        double normalizedScore = matchingSkillsCount > 0 ? (totalCompatibilityScore / matchingSkillsCount) * 100 : 0.0;
        return normalizedScore > 100 ? 100 : (normalizedScore < 0 ? 0 : normalizedScore);
    }

}
