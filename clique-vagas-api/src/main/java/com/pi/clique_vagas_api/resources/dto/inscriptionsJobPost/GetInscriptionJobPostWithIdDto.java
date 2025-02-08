package com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost;

import java.time.ZonedDateTime;

import com.pi.clique_vagas_api.resources.dto.user.GetNameAndEmailUserDto;

public record GetInscriptionJobPostWithIdDto(
        Long id,
        GetNameAndEmailUserDto user,
        Double pontuation,
        ZonedDateTime inscriptionDate) {

}
