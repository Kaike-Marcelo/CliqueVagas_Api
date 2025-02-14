package com.pi.clique_vagas_api.resources.dto.jobPost;

public record GetAllJobPostDto(
        JobPostWithIdDto jobPost,
        Long companyId,
        String email,
        String companyName,
        byte[] fileProfile) {

}
