package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import com.pi.clique_vagas_api.events.InternSkillChangedEvent;
import com.pi.clique_vagas_api.events.JobPostingSkillChangedEvent;
import com.pi.clique_vagas_api.model.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.model.skills.Skill_Intern_Model;
import com.pi.clique_vagas_api.model.skills.Skill_JobPosting_Model;
import com.pi.clique_vagas_api.repository.InscriptionsJobPostingRepository;
import com.pi.clique_vagas_api.resources.enums.Status;
import com.pi.clique_vagas_api.service.JobPostingService;
import com.pi.clique_vagas_api.service.inscriptionsJobPost.PontuationService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

public class SkillEventListener {

    @Autowired
    private PontuationService pontuationService;

    @Autowired
    private InternService internService;

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private Skill_Intern_Service skillInternService;

    @Autowired
    private Skill_JobPost_Service skillJobPostService;

    @Autowired
    private InscriptionsJobPostingRepository inscriptionsJobPostRepository;

    @EventListener
    public void handleSkillVagaAlterada(JobPostingSkillChangedEvent event) {
        Long jobPostingId = event.getJobPostingId();

        var jobPost = jobPostingService.findById(jobPostingId);

        List<InscriptionsJobPostingModel> inscricoes = inscriptionsJobPostRepository
                .findAllByJobPostingId(jobPost);

        for (InscriptionsJobPostingModel inscricao : inscricoes) {

            var Intern = internService.getInternByIdUser(inscricao.getUserId());

            List<Skill_Intern_Model> skillsEstagiario = skillInternService
                    .getSkillsFromInternByUserId(Intern);
            List<Skill_JobPosting_Model> skillsVaga = skillJobPostService
                    .getSkillsFromPostByUserId(inscricao.getJobPostingId());

            Double novaPontuacao = pontuationService.calculatePontuation(skillsEstagiario, skillsVaga);
            inscricao.setPontuation(novaPontuacao);
            inscriptionsJobPostRepository.save(inscricao);
        }
    }

    @EventListener
    public void handleSkillEstagiarioAlterada(InternSkillChangedEvent event) {
        Long userId = event.getUserId();

        var intern = internService.getInternById(userId);

        List<InscriptionsJobPostingModel> inscricoes = inscriptionsJobPostRepository
                .findAllByUserIdAndJobPostingStatus(intern.getUserId(), Status.ACTIVE);

        for (InscriptionsJobPostingModel inscricao : inscricoes) {

            List<Skill_Intern_Model> skillsEstagiario = skillInternService
                    .getSkillsFromInternByUserId(intern);
            List<Skill_JobPosting_Model> skillsVaga = skillJobPostService
                    .getSkillsFromPostByUserId(inscricao.getJobPostingId());

            Double novaPontuacao = pontuationService.calculatePontuation(skillsEstagiario, skillsVaga);
            inscricao.setPontuation(novaPontuacao);
            inscriptionsJobPostRepository.save(inscricao);
        }
    }

}
