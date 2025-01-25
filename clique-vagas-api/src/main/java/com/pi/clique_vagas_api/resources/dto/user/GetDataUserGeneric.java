package com.pi.clique_vagas_api.resources.dto.user;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.address.AddressWithIdDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDataUserGeneric {
    private AddressWithIdDto address;
    private UserModel user;
}
