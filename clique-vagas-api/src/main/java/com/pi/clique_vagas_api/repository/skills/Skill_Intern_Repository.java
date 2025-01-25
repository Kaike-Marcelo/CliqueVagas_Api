package com.pi.clique_vagas_api.repository.skills;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;

@Repository
public interface Skill_Intern_Repository extends JpaRepository<Skill_Intern_Model, Long> {
    List<Skill_Intern_Model> findAllByIdIntern(InternModel idIntern);

    Skill_Intern_Model findByIdInternAndIdSkill(InternModel idIntern, SkillModel idSkill);

    Optional<Skill_Intern_Model> findByIdAndIdIntern(Long id, InternModel idIntern);
}
