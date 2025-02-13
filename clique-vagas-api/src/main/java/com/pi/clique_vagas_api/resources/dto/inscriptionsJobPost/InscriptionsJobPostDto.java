package com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;

public record InscriptionsJobPostDto(
                JobPostingModel jobPostingId,
                UserModel userId) {

}
