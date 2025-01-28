package com.pi.clique_vagas_api.resources.dto.skill;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListSkillsDto {
    List<Skill_Intermediate_Dto> skillJobPost;
}
