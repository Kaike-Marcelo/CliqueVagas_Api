package com.pi.clique_vagas_api.resources.dto.user.company;

public record PostCompanyDto(
        String companyName,
        String cnpj,
        String telephoneResponsible,
        String sectorOfOperation,
        String websiteLink) {

}
