package com.pi.clique_vagas_api.resources.dto.jobPost;

import java.time.ZonedDateTime;

import com.pi.clique_vagas_api.resources.enums.Status;

public record JobPostWithIdDto(
                Long id,
                String title,
                String description,
                Status jobPostingStatus,
                String address,
                ZonedDateTime applicationDeadline) {

}
