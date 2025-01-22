package com.pi.clique_vagas_api.resources.dto.address;

public record AddressWithIdDto(
        Long id,
        String cep,
        String street,
        String number,
        String neighborhood,
        String city,
        String state) {

}
