package com.pi.clique_vagas_api.service.users.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.user.intern.PostInternDto;
import com.pi.clique_vagas_api.resources.dto.user.UserDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternProfileDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.CertificateService;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@Service
public class InternProfileService {

    @Autowired
    private InternService internService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private Skill_Intern_Service skillInternService;

    @Autowired
    private CertificateService certificateService;

    @Transactional
    public InternProfileDto getDataByIdUser(UserModel user, UserDto userDto) {

        if (user.getRole() != UserRole.INTERN)
            throw new EventNotFoundException("User is not an intern");

        var intern = internService.getInternByUser(user);
        var address = addressService.getAddressDtoByUserId(user);
        var skills = skillInternService.getSkillsDtoByInternId(intern);
        var certificates = certificateService.getCertificatesByInternId(intern);

        PostInternDto internDto = new PostInternDto(
                intern.getDateOfBirth(),
                intern.getSex(),
                intern.getCpf(),
                intern.getEducationalInstitution(),
                intern.getAreaOfInterest(),
                intern.getYearOfEntry(),
                intern.getExpectedGraduationDate());

        InternProfileDto getInternDto = new InternProfileDto();
        getInternDto.setUser(userDto);
        getInternDto.setAddress(address);
        getInternDto.setIntern(internDto);
        getInternDto.setSkillIntern(skills);
        getInternDto.setCertificates(certificates);

        return getInternDto;
    }

}
