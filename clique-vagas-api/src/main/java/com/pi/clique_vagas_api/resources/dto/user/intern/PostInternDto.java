package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.sql.Date;

public record PostInternDto(
                Date dateOfBirth,
                String sex,
                String cpf,
                String educationalInstitution,
                String areaOfInterest,
                String yearOfEntry,
                Date expectedGraduationDate) {
}
