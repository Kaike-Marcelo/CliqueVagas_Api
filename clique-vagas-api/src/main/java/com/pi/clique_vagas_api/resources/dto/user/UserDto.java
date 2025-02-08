package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.enums.UserRole;

public record UserDto(
        String firstName,
        String lastName,
        UserRole role,
        String cpf,
        String phone,
        String email,
        String password) {

}
