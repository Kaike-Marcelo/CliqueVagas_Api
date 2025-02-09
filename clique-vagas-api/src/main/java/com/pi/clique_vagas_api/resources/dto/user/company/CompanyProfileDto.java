package com.pi.clique_vagas_api.resources.dto.user.company;

import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyProfileDto extends GetDataUserGeneric {
    private PostCompanyDto company;
}
