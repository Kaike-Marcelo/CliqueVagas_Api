package com.pi.clique_vagas_api.resources.dto.jobPost;

import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;

import java.util.List;

public record JobPostingResponse(
        Long id,
        String title,
        String description,
        String address,
        int compatibilityScore,
        List<Skill_JobPosting_Model> skills) {
}
