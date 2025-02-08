package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.events.InternSkillChangedEvent;
import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.skills.Skill_Intern_Repository;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_Dto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.utils.DateUtils;

import jakarta.transaction.Transactional;

@Service
public class Skill_Intern_Service {

    @Autowired
    private Skill_Intern_Repository skillInternRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public Skill_Intern_Model createSkillIntern(SkillModel skill, InternModel intern,
            Skill_Intermediate_Dto skillIntern) {

        var isDataExists = skillInternRepository
                .findByIdInternAndIdSkill(intern, skill);

        if (isDataExists != null) {
            throw new EventNotFoundException("Skill already exists for this intern");
        }

        Skill_Intern_Model skillInternModel = new Skill_Intern_Model(
                null,
                intern,
                skill,
                skillIntern.getProficiencyLevel(),
                DateUtils.nowInZone(),
                null);

        eventPublisher.publishEvent(new InternSkillChangedEvent(this, intern.getUserId().getUserId()));
        return skillInternRepository.save(skillInternModel);
    }

    public Skill_Intern_Model getSkillInternById(Long id) {
        return skillInternRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Skill not found"));
    }

    public Skill_Intern_Model getSkillInternByIdAndIdIntern(Long id, InternModel intern) {
        return skillInternRepository.findByIdAndIdIntern(id, intern)
                .orElseThrow(() -> new EventNotFoundException("Skill not found"));
    }

    public List<Skill_Intern_Model> getSkillsFromInternByUserId(InternModel intern) {
        var skillIntern = skillInternRepository.findAllByIdIntern(intern);
        return skillIntern;
    }

    public List<Skill_Intermediate_WithIdDto> getSkillsDtoByInternId(InternModel intern) {
        var skillIntern = skillInternRepository.findAllByIdIntern(intern);

        return skillIntern.stream()
                .map(skill -> new Skill_Intermediate_WithIdDto(
                        skill.getId(),
                        skill.getIdSkill(),
                        skill.getProficiencyLevel()))
                .toList();
    }

    @Transactional
    public Skill_Intern_Model updateSkillIntern(Skill_Intermediate_WithIdDto body, InternModel intern) {
        var skillIntern = getSkillInternByIdAndIdIntern(body.id(), intern);

        skillIntern.setProficiencyLevel(body.proficiencyLevel());
        skillIntern.setUpdatedAt(DateUtils.nowInZone());
        eventPublisher.publishEvent(new InternSkillChangedEvent(this, intern.getUserId().getUserId()));
        return skillInternRepository.save(skillIntern);
    }

    public void deleteSkillIntern(Long id) {
        var skillIntern = getSkillInternById(id);
        eventPublisher.publishEvent(
                new InternSkillChangedEvent(this, skillIntern.getIdIntern().getUserId().getUserId()));
        skillInternRepository.deleteById(id);
    }

}
