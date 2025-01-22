package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.skills.Skill_Intern_Repository;
import com.pi.clique_vagas_api.resources.dto.skill.Intern.Skill_Intern_Dto;
import com.pi.clique_vagas_api.service.users.InternService;

import jakarta.transaction.Transactional;

@Service
public class Skill_Intern_Service {

    @Autowired
    private Skill_Intern_Repository skillInternRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private InternService internService;

    @Transactional
    public Skill_Intern_Model createSkillIntern(Skill_Intern_Dto skillIntern) {

        var skill = skillService.getSkillById(skillIntern.getIdSkill());

        var intern = internService.getInternByIdUser(skillIntern.getIdUser());

        if (skill == null || intern == null) {
            throw new EventNotFoundException("Skill or Intern not found");
        }

        var isDataExists = skillInternRepository
                .findByIdInternAndIdSkill(intern, skill);

        if (isDataExists != null) {
            throw new EventNotFoundException("Skill already exists for this intern");
        }

        Skill_Intern_Model skillInternModel = new Skill_Intern_Model(
                null,
                intern,
                skill,
                skillIntern.getProficiencyLevel());

        skillInternRepository.save(skillInternModel);
        return skillInternModel;
    }

    public List<Skill_Intern_Model> getSkillsFromInternByUserId(InternModel intern) {
        var skillIntern = skillInternRepository.findAllByIdIntern(intern);
        return skillIntern;
    }

    public void deleteSkillIntern(Long id) {
        skillInternRepository.deleteById(id);
    }

}
