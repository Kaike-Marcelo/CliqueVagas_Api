package com.pi.clique_vagas_api.resources.dto.skill.jobPost;

import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.resources.enums.skill.ProficiencyLevel;

public record GetSkillsJobPosting(
        Long id,
        SkillModel skillModel,
        ProficiencyLevel proficiencyLevel) {
}
