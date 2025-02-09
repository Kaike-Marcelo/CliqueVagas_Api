package com.pi.clique_vagas_api.resources.dto.user.company;

import com.pi.clique_vagas_api.resources.dto.user.UserDto;

import jakarta.validation.constraints.NotNull;

public record CompanyDto(
        @NotNull Long id,
        @NotNull UserDto user,
        @NotNull String companyName,
        @NotNull String cnpj,
        @NotNull String telephoneResponsible,
        @NotNull String sectorOfOperation,
        @NotNull String websiteLink) {

}
