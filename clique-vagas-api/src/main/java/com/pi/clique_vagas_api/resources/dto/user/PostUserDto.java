package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.enums.UserRole;

public record PostUserDto(
        String firstName,
        String lastName,
        UserRole role,
        String phone,
        String email,
        String password,
        String description) {

}
