package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserWithAddressDto {
    private GetAddressDto address;
    private UserDto user;
}
