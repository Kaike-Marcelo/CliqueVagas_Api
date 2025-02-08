package com.pi.clique_vagas_api.service.skills;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.events.JobPostingSkillChangedEvent;
import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;
import com.pi.clique_vagas_api.repository.skills.Skill_JobPost_Repository;
import com.pi.clique_vagas_api.resources.dto.jobPost.GetJobPostDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_Dto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.resources.dto.skill.jobPost.GetSkillsJobPosting;
import com.pi.clique_vagas_api.utils.DateUtils;

import jakarta.transaction.Transactional;

@Service
public class Skill_JobPost_Service {

    @Autowired
    private Skill_JobPost_Repository skillPostRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public Skill_JobPosting_Model createSkillPost(SkillModel skill, JobPostingModel post,
            Skill_Intermediate_Dto skillPost) {

        var isDataExists = skillPostRepository
                .findByIdJobPostingAndIdSkill(post, skill);

        if (isDataExists.isPresent()) {
            throw new EventNotFoundException("Skill already exists for this post");
        }

        Skill_JobPosting_Model skillPostModel = new Skill_JobPosting_Model(
                null,
                post,
                skill,
                skillPost.getProficiencyLevel(),
                DateUtils.nowInZone(),
                null);

        eventPublisher.publishEvent(new JobPostingSkillChangedEvent(this, post.getId()));
        return skillPostRepository.save(skillPostModel);
    }

    public Skill_JobPosting_Model getSkillPostById(Long id) {
        return skillPostRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Skill not found"));
    }

    public Skill_JobPosting_Model getSkillPostByIdAndIdPost(Long id, JobPostingModel post) {
        return skillPostRepository.findByIdAndIdJobPosting(id, post)
                .orElseThrow(() -> new EventNotFoundException("Skill not found"));
    }

    public Skill_JobPosting_Model getSkillPostByIdJobPostingIdAndIdSkill(JobPostingModel post, SkillModel skill) {
        return skillPostRepository.findByIdJobPostingAndIdSkill(post, skill)
                .orElseThrow(() -> new EventNotFoundException("Skill not found"));
    }

    public List<Skill_JobPosting_Model> getSkillsFromPostByUserId(JobPostingModel post) {
        var skillPost = skillPostRepository.findAllByIdJobPosting(post);
        return skillPost;
    }

    public List<Skill_Intermediate_WithIdDto> getSkillsDtoByPostId(JobPostingModel post) {
        var skillPost = skillPostRepository.findAllByIdJobPosting(post);

        return skillPost.stream()
                .map(skill -> new Skill_Intermediate_WithIdDto(
                        skill.getId(),
                        skill.getIdSkill(),
                        skill.getProficiencyLevel()))
                .toList();
    }

    public List<GetJobPostDto> findAllCompletePostsByIdPost(List<JobPostingModel> posts) {

        List<GetJobPostDto> postDto = new ArrayList<>();

        for (JobPostingModel post : posts) {
            var skills = skillPostRepository.findAllByIdJobPosting(post);

            List<GetSkillsJobPosting> skillDtos = skills.stream()
                    .map(skill -> new GetSkillsJobPosting(skill.getId(), skill.getIdSkill(),
                            skill.getProficiencyLevel()))
                    .toList();

            JobPostWithIdDto jobPostWithIdDto = new JobPostWithIdDto(
                    post.getId(),
                    post.getTitle(),
                    post.getDescription(),
                    post.getJobPostingStatus(),
                    post.getAddress(),
                    post.getApplicationDeadline());

            postDto.add(new GetJobPostDto(jobPostWithIdDto, skillDtos));
        }
        return postDto;
    }

    @Transactional
    public Skill_JobPosting_Model updateSkillPost(Skill_Intermediate_WithIdDto body, JobPostingModel post) {
        var skillPost = getSkillPostByIdAndIdPost(body.id(), post);

        skillPost.setProficiencyLevel(body.proficiencyLevel());
        skillPost.setUpdatedAt(DateUtils.nowInZone());
        eventPublisher.publishEvent(new JobPostingSkillChangedEvent(this, post.getId()));

        return skillPostRepository.save(skillPost);
    }

    public void deleteSkillPost(Long id) {
        var skillPost = getSkillPostById(id);
        eventPublisher.publishEvent(new JobPostingSkillChangedEvent(this, skillPost.getId()));
        skillPostRepository.deleteById(id);
    }

}
