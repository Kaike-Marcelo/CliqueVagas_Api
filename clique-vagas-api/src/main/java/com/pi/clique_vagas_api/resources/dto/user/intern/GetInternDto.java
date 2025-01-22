package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.util.List;

import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInternDto extends GetAddressDto {
    private InternModel intern;
    private List<Skill_Intermediate_WithIdDto> skillIntern;
}
