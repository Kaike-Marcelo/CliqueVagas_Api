package com.pi.clique_vagas_api.resources.dto.skill.jobPost;

import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill_JobPost_Dto extends Skill_Intermediate_Dto {
    private Long idJobPost;
}
