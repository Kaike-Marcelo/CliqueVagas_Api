package com.pi.clique_vagas_api.resources.dto.resume;

import java.util.List;

import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;

public record ResumeDto(
                String fullName,
                String email,
                String phone,
                String educationalInstitution,
                String areaOfInterest,
                byte[] profileImage,
                List<Skill_Intermediate_WithIdDto> skills,
                List<CertificateWithIdDto> certificates) {
}