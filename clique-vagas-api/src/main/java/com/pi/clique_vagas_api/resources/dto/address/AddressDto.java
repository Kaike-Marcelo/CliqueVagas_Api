package com.pi.clique_vagas_api.resources.dto.address;

import com.pi.clique_vagas_api.resources.dto.user.UserDto;

public record AddressDto(
                Long id,
                UserDto user,
                String cep,
                String street,
                String number,
                String neighborhood,
                String city,
                String state) {

}
