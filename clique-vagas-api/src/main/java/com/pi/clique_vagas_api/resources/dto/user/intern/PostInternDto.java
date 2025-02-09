package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;

public record PostInternDto(
        @NotNull Date dateOfBirth,
        @NotNull String sex,
        @NotNull String cpf,
        @NotNull String educationalInstitution,
        @NotNull String areaOfInterest,
        @NotNull String yearOfEntry,
        @NotNull Date expectedGraduationDate) {
}
