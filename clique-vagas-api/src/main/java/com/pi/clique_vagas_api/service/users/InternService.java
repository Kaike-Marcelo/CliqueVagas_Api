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
import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.GetInternDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class InternService {

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    @Lazy
    private Skill_Intern_Service skillInternService;

    @Transactional
    public InternModel createIntern(CreateInternDto body) {

        try {

            var userDto = body.getUser();
            var addressDto = body.getAddress();
            var internDto = body.getIntern();

            if (userDto.role() != UserRole.INTERN)
                throw new EventNotFoundException("User is not an intern");

            var user = userService.createUser(userDto);

            addressService.createAddress(addressDto, user);

            var internModel = new InternModel(
                    null,
                    user,
                    internDto.dateOfBirth(),
                    internDto.sex(),
                    internDto.educationalInstitution(),
                    internDto.areaOfInterest(),
                    internDto.yearOfEntry(),
                    internDto.expectedGraduationDate(),
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

    @Transactional
    public GetInternDto getDataByIdUser(Long id) {
        var user = userService.getUserById(id);

        if (user.getRole() != UserRole.INTERN)
            throw new EventNotFoundException("User is not an intern");

        var intern = internRepository.findByUserId(user);
        var address = addressService.getAddressDtoByUserId(user);
        var skills = skillInternService.getSkillsDtoByInternId(intern);

        GetInternDto getInternDto = new GetInternDto();
        getInternDto.setAddress(address);
        getInternDto.setIntern(intern);
        getInternDto.setSkillIntern(skills);

        return getInternDto;
    }

    public InternModel getInternByIdUser(UserModel user) {
        // var user = userService.getUserById(id);
        return internRepository.findByUserId(user);
    }

    public void deleteIntern(Long id) {
        internRepository.deleteById(id);
    }
}
