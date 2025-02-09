package com.pi.clique_vagas_api.resources.dto.user.intern;

import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGenericDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInternDto extends GetDataUserGenericDto {
    private PostInternDto intern;
}
