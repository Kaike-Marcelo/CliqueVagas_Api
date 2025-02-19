package com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.skills.Skill_JobPost_Service;

@Service
public class PontuationService {

    @Autowired
    private Skill_Intern_Service skill_Intern_Service;

    @Autowired
    private Skill_JobPost_Service skill_JobPost_Service;

    public Double calculatePontuation(InternModel intern, JobPostingModel jobPost) {

        var skillJobPosts = skill_JobPost_Service.getSkillsDtoByPostId(jobPost);
        if (skillJobPosts.isEmpty()) {
            return 0.0;
        }
        var skillInterns = skill_Intern_Service.getSkillsDtoByInternId(intern).iterator();

        Double totalCompatibilityScore = 0.0;

        while (skillInterns.hasNext()) {
            Skill_Intermediate_WithIdDto skillIntern = skillInterns.next();
            for (Skill_Intermediate_WithIdDto skillPost : skillJobPosts) {
                if (skillPost.idSkill().equals(skillIntern.idSkill())) {
                    totalCompatibilityScore += skillPost.proficiencyLevel()
                            .calculateSkillCompatibility(skillIntern.proficiencyLevel());
                    skillInterns.remove();
                    break;
                }
            }
        }

        double normalizedScore = (totalCompatibilityScore / skillJobPosts.size()) * 100;
        return Math.min(100, Math.max(0, normalizedScore));
    }

}
