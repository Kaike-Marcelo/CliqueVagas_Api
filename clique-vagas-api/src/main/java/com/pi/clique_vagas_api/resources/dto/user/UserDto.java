package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.enums.UserRole;

public record UserDto(
                Long userId,
                String firstName,
                String lastName,
                byte[] urlImageProfile,
                UserRole role,
                String phone,
                String email,
                String password,
                String description) {
}
