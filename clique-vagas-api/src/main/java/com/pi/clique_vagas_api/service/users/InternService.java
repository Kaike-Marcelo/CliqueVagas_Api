package com.pi.clique_vagas_api.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.users.InternRepository;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternProfileDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.CertificateService;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class InternService {

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    @Lazy
    private Skill_Intern_Service skillInternService;

    @Autowired
    private CertificateService certificateService;

    @Transactional
    public InternModel createIntern(InternDto body, UserModel user) {

        try {
            var internModel = new InternModel(
                    null,
                    user,
                    body.dateOfBirth(),
                    body.sex(),
                    body.educationalInstitution(),
                    body.areaOfInterest(),
                    body.yearOfEntry(),
                    body.expectedGraduationDate(),
                    DateUtils.nowInZone(),
                    null);

            return internRepository.save(internModel);

        } catch (Exception ex) {
            throw new EventNotFoundException("Error creating intern: " + ex.getMessage());
        }
    }

    public List<InternModel> getAllInterns() {
        return internRepository.findAll();
    }

    public InternModel getInternById(Long id) {
        return internRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Intern not found with ID: " + id));
    }

    public InternModel getInternByUser(UserModel user) {
        return internRepository.findByUserId(user)
                .orElseThrow(() -> new EventNotFoundException("Intern not found"));
    }

    @Transactional
    public InternProfileDto getDataByIdUser(UserModel user) {

        if (user.getRole() != UserRole.INTERN)
            throw new EventNotFoundException("User is not an intern");

        var intern = getInternByIdUser(user);
        var address = addressService.getAddressDtoByUserId(user);
        var skills = skillInternService.getSkillsDtoByInternId(intern);
        var certificates = certificateService.getCertificatesByInternId(intern);

        InternDto internDto = new InternDto(
                intern.getDateOfBirth(),
                intern.getSex(),
                intern.getEducatinoalInstitution(),
                intern.getAreaOfInterest(),
                intern.getYearOfEntry(),
                intern.getExpectedGraduationDate());

        InternProfileDto getInternDto = new InternProfileDto();
        getInternDto.setUser(user);
        getInternDto.setAddress(address);
        getInternDto.setIntern(internDto);
        getInternDto.setSkillIntern(skills);
        getInternDto.setCertificates(certificates);

        return getInternDto;
    }

    public InternModel getInternByIdUser(UserModel user) {
        return internRepository.findByUserId(user).orElseThrow(() -> new EventNotFoundException("Intern not found"));
    }

    public void deleteIntern(Long id) {
        internRepository.deleteById(id);
    }
}
