package com.pi.clique_vagas_api.resources.dto.user.company;

import com.pi.clique_vagas_api.resources.dto.user.UserWithAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyDto extends UserWithAddress {
    private CompanyDto company;
}
