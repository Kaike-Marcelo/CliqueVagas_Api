package com.pi.clique_vagas_api.resources.dto.user.intern;

import com.pi.clique_vagas_api.resources.dto.user.UserDto;

public record InternDto(
        Long id,
        UserDto user,
        String cpf,
        String dateOfBirth,
        String sex,
        String educationalInstitution,
        String areaOfInterest,
        String yearOfEntry,
        String expectedGraduationDate) {

}
