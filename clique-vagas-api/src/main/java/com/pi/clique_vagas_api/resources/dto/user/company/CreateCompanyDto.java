package com.pi.clique_vagas_api.resources.dto.user.company;

import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGenericDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyDto extends GetDataUserGenericDto {
    private PostCompanyDto company;
}
