package com.pi.clique_vagas_api.model.skills;

import org.hibernate.annotations.DynamicUpdate;

import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.enums.skill.ProficiencyLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skill_intern")
@DynamicUpdate
public class Skill_Intern_Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_intern")
    private InternModel idIntern;

    @ManyToOne
    @JoinColumn(name = "id_skill")
    private SkillModel idSkill;

    @Column(name = "proficiency_level", length = 20)
    private ProficiencyLevel proficiencyLevel;
}
