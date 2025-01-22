package com.pi.clique_vagas_api.resources.dto.skill;

import com.pi.clique_vagas_api.resources.enums.skill.ProficiencyLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill_Intermediate_Dto {
        private Long idSkill;
        private ProficiencyLevel proficiencyLevel;
}
