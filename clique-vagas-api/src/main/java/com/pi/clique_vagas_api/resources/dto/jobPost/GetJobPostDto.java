package com.pi.clique_vagas_api.resources.dto.jobPost;

import java.util.List;

import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetJobPostDto {

        private JobPostingModel jobPost;
        private List<Skill_JobPosting_Model> skillJobPost;

}
