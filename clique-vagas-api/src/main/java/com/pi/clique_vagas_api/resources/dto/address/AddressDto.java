package com.pi.clique_vagas_api.resources.dto.address;

public record AddressDto(
                String cep,
                String street,
                String number,
                String neighborhood,
                String city,
                String state,
                String country) {
}
