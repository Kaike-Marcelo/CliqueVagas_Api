package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.util.List;

import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInternDto extends GetAddressDto {
    private InternModel intern;
    private List<Skill_Intern_Model> skillIntern;
}
