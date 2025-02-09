package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.dto.address.PostAddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithAddress {
    private PostUserDto user;
    private PostAddressDto address;
}
