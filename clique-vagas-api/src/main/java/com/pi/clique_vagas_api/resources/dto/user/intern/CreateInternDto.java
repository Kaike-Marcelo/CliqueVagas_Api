package com.pi.clique_vagas_api.resources.dto.user.intern;

import com.pi.clique_vagas_api.resources.dto.user.UserWithAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInternDto extends UserWithAddress {
    private PostInternDto intern;
}
