package com.pi.clique_vagas_api.resources.dto.resume;

import java.sql.Date;
import java.util.List;

import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;

public record ResumeDto(
        String fullName,
        String email,
        String phone,
        String description,
        String educationalInstitution,
        String areaOfInterest,
        String yearOfEntry,
        Date expectedGraduationDate,
        byte[] profileImage,
        GetAddressDto address,
        List<Skill_Intermediate_WithIdDto> skills,
        List<CertificateWithIdDto> certificates) {
}