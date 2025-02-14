package com.pi.clique_vagas_api.model.skills;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
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
@Table(name = "skill_job_posting")
@DynamicUpdate
public class Skill_JobPosting_Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_job_posting")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private JobPostingModel idJobPosting;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_skill")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SkillModel idSkill;

    @Column(name = "proficiency_level", length = 20)
    private ProficiencyLevel proficiencyLevel;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
