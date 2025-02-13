package com.pi.clique_vagas_api.repository.skills;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;

@Repository
public interface Skill_JobPost_Repository extends JpaRepository<Skill_JobPosting_Model, Long> {

    List<Skill_JobPosting_Model> findAllByIdJobPosting(JobPostingModel model);

    Optional<Skill_JobPosting_Model> findByIdJobPostingAndIdSkill(JobPostingModel model, SkillModel idSkill);

    Optional<Skill_JobPosting_Model> findByIdAndIdJobPosting(Long id, JobPostingModel model);

}
