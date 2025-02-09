package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.resources.dto.address.PostAddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDataUserGenericDto {
    private PostAddressDto address;
    private PostUserDto user;
}
