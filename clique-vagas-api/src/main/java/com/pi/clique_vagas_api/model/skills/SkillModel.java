package com.pi.clique_vagas_api.model.skills;

import org.hibernate.annotations.DynamicUpdate;

import com.pi.clique_vagas_api.resources.enums.skill.TypeSkill;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
@DynamicUpdate
public class SkillModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    @Column(name = "type", length = 50)
    @NotNull
    private TypeSkill type;
}
