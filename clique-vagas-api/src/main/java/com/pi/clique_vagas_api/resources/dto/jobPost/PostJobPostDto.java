package com.pi.clique_vagas_api.resources.dto.jobPost;

import com.pi.clique_vagas_api.resources.dto.skill.ListSkillsDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostJobPostDto extends ListSkillsDto {
        private JobPostDto jobPost;
}
