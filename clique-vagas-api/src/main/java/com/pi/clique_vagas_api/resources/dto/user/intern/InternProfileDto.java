package com.pi.clique_vagas_api.resources.dto.user.intern;

import java.util.List;

import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.resources.dto.user.GetUserWithAddressDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternProfileDto extends GetUserWithAddressDto {
    private PostInternDto intern;
    private List<Skill_Intermediate_WithIdDto> skillIntern;
    private List<CertificateWithIdDto> certificates;
}
