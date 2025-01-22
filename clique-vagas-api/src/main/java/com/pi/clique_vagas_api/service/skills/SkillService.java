package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.repository.skills.SkillRepository;
import com.pi.clique_vagas_api.resources.dto.skill.CreateSkillDto;
import com.pi.clique_vagas_api.resources.enums.skill.TypeSkill;

import jakarta.transaction.Transactional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public SkillModel add(CreateSkillDto skillDto) {

        var isNameExists = skillRepository.findByNameAndType(skillDto.name(), skillDto.type());

        if (!isNameExists.isEmpty()) {
            throw new EventNotFoundException("Skill already exists");
        }

        var skill = new SkillModel(
                null,
                skillDto.name(),
                skillDto.type());
        var skillSaved = skillRepository.save(skill);
        return skillSaved;
    }

    public List<SkillModel> getAllSkills() {
        return skillRepository.findAll();
    }

    public SkillModel getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Skill not found with ID: " + id));
    }

    public List<SkillModel> getSkillByType(TypeSkill type) {
        return skillRepository.findAllByType(type);
    }

    @Transactional
    public void updateSkillById(Long skillId, CreateSkillDto skillDto) {
        var skillModel = skillRepository.findById(skillId);

        if (skillModel.isPresent()) {
            var skill = skillModel.get();

            if (skillDto.name() != null)
                skill.setName(skillDto.name());
            if (skillDto.type() != null)
                skill.setType(skillDto.type());

            skillRepository.save(skill);
        } else {
            throw new EventNotFoundException("Skill not found with ID: " + skillId);
        }
    }

    public void deleteById(Long skillId) {
        var skillModel = skillRepository.findById(skillId);

        if (skillModel.isPresent()) {
            skillRepository.deleteById(skillId);
        } else {
            throw new EventNotFoundException("Skill not found with ID: " + skillId);
        }
    }
}
