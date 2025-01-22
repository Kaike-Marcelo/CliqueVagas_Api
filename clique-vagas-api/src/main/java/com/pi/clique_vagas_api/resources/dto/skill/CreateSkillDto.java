package com.pi.clique_vagas_api.resources.dto.skill;

import com.pi.clique_vagas_api.resources.enums.skill.TypeSkill;

public record CreateSkillDto(String name, TypeSkill type) {

}
