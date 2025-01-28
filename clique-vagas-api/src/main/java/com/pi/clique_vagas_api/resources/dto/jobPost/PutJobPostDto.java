package com.pi.clique_vagas_api.resources.dto.jobPost;

import com.pi.clique_vagas_api.resources.dto.skill.ListSkillsDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutJobPostDto extends ListSkillsDto {
    private JobPostWithIdDto jobPost;
}
