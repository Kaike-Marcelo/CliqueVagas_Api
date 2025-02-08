package com.pi.clique_vagas_api.service.inscriptionsJobPost;

import java.util.List;

import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;

public class PontuationService {

    public Double calculatePontuation(List<Skill_Intern_Model> skillInterns,
            List<Skill_JobPosting_Model> skillJobPosts) {
        Double totalCompatibilityScore = 0.0;
        int matchingSkillsCount = 0;

        for (Skill_JobPosting_Model skillPost : skillJobPosts) {
            for (Skill_Intern_Model skillIntern : skillInterns) {
                if (skillPost.getIdSkill().equals(skillIntern.getIdSkill())) {
                    totalCompatibilityScore += skillPost.getProficiencyLevel()
                            .calculateSkillCompatibility(skillIntern.getProficiencyLevel());
                    matchingSkillsCount++;
                }
            }
        }

        double normalizedScore = matchingSkillsCount > 0 ? (totalCompatibilityScore / matchingSkillsCount) * 100 : 0.0;
        return normalizedScore > 100 ? 100 : (normalizedScore < 0 ? 0 : normalizedScore);
    }

}
