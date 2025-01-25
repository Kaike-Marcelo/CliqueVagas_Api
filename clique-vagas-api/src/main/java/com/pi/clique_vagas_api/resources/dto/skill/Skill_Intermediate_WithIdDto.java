package com.pi.clique_vagas_api.resources.dto.skill;

import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.resources.enums.skill.ProficiencyLevel;

public record Skill_Intermediate_WithIdDto(
        Long id,
        SkillModel idSkill,
        ProficiencyLevel proficiencyLevel) {

}
