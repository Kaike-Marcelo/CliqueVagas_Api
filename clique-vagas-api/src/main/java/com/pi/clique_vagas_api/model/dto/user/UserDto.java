package com.pi.clique_vagas_api.model.dto.user;

import com.pi.clique_vagas_api.model.users.UserRole;

public record UserDto(String firstName, String lastName, UserRole role, String cpf, String phone, String email,
        String password) {

}
