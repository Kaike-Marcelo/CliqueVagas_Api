package com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost;

import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;

public record InscriptionsJobPostDto(
        JobPostingModel jobPostingId,
        InternModel internId) {

}
