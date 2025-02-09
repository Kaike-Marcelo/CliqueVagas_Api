package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.sql.Date;

import com.pi.clique_vagas_api.resources.dto.user.UserDto;

public record InternDto(
                Long id,
                UserDto user,
                String cpf,
                Date dateOfBirth,
                String sex,
                String educationalInstitution,
                String areaOfInterest,
                String yearOfEntry,
                Date expectedGraduationDate) {

}
