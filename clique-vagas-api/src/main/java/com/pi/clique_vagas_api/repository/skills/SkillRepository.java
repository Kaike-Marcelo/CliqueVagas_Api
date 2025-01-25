package com.pi.clique_vagas_api.repository.skills;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.resources.enums.skill.TypeSkill;

@Repository
public interface SkillRepository extends JpaRepository<SkillModel, Long> {
    public List<SkillModel> findAllByType(TypeSkill type);

    public List<SkillModel> findByNameAndType(String name, TypeSkill type);

}
