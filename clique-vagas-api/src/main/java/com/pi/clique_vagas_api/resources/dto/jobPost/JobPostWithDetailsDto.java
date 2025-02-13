package com.pi.clique_vagas_api.resources.dto.jobPost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPostWithDetailsDto {
    private GetJobPostDto jobPosting;
    private Long likeCount;
}
