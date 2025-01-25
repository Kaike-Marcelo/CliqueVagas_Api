package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.sql.Date;

public record GetInternWithIdDto(
        Long id,
        Date dateOfBirth,
        String sex,
        String educationalInstitution,
        String areaOfInterest,
        String yearOfEntry,
        Date expectedGraduationDate) {

}
