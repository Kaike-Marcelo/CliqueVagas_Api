package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.dto.address.AddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithAddress {
    private UserDto user;
    private AddressDto address;
}
