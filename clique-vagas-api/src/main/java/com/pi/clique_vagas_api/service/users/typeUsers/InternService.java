package com.pi.clique_vagas_api.service.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.users.InternRepository;
import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.PostInternDto;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class InternService {

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public InternModel createIntern(CreateInternDto body) {

        if (internRepository.findByCpf(body.getIntern().cpf()) != null)
            throw new EventNotFoundException("Intern already exists with this CPF");

        var user = userService.createUser(body.getUser());
        addressService.createAddress(body.getAddress(), user);

        var internModel = new InternModel(
                null,
                user,
                body.getIntern().cpf(),
                body.getIntern().dateOfBirth(),
                body.getIntern().sex(),
                body.getIntern().educationalInstitution(),
                body.getIntern().areaOfInterest(),
                body.getIntern().yearOfEntry(),
                body.getIntern().expectedGraduationDate(),
                DateUtils.nowInZone(),
                null);

        return internRepository.save(internModel);
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

    public InternModel updateIntern(Long id, PostInternDto body) {
        var intern = getInternById(id);

        intern.setCpf(body.cpf());
        intern.setDateOfBirth(body.dateOfBirth());
        intern.setSex(body.sex());
        intern.setEducationalInstitution(body.educationalInstitution());
        intern.setAreaOfInterest(body.areaOfInterest());
        intern.setYearOfEntry(body.yearOfEntry());
        intern.setExpectedGraduationDate(body.expectedGraduationDate());
        intern.setUpdatedAt(DateUtils.nowInZone());

        return internRepository.save(intern);
    }

    public void deleteIntern(String username) {
        var user = userService.findByEmail(username);
        if (user == null) {
            throw new EventNotFoundException("User not found");
        }
        internRepository.deleteByUserId(user);
    }
}
