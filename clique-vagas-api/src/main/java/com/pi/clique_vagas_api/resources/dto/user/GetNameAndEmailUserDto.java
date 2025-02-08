package com.pi.clique_vagas_api.resources.dto.user;

public record GetNameAndEmailUserDto(
        String firstName,
        String lastName,
        String email,
        byte[] fileProfile) {

}
