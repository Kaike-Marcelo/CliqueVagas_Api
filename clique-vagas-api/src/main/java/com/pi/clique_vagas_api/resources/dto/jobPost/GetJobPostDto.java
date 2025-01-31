package com.pi.clique_vagas_api.resources.dto.jobPost;

import java.util.List;

import com.pi.clique_vagas_api.resources.dto.skill.jobPost.GetSkillsJobPosting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetJobPostDto {

        private JobPostWithIdDto jobPost;
        private List<GetSkillsJobPosting> skillJobPost;

}
