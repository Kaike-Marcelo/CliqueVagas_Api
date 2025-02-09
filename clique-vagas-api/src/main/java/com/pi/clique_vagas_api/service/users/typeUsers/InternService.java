package com.pi.clique_vagas_api.service.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.users.InternRepository;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class InternService {

    @Autowired
    private InternRepository internRepository;

    @Transactional
    public InternModel createIntern(InternDto body, UserModel user) {

        if (internRepository.findByCpf(body.cpf()) != null)
            throw new EventNotFoundException("Intern already exists with CPF: " + body.cpf());

        try {
            var internModel = new InternModel(
                    null,
                    user,
                    body.cpf(),
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

    public InternModel getInternByIdUser(UserModel user) {
        return internRepository.findByUserId(user).orElseThrow(() -> new EventNotFoundException("Intern not found"));
    }

    public void deleteIntern(Long id) {
        internRepository.deleteById(id);
    }
}
