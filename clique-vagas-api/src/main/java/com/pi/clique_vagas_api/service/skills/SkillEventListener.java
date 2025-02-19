package com.pi.clique_vagas_api.service.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pi.clique_vagas_api.events.InternSkillChangedEvent;
import com.pi.clique_vagas_api.events.JobPostingSkillChangedEvent;
import com.pi.clique_vagas_api.model.jobPost.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.repository.jobPosting.InscriptionsJobPostingRepository;
import com.pi.clique_vagas_api.resources.enums.Status;
import com.pi.clique_vagas_api.service.jobPost.JobPostingService;
import com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost.PontuationService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@Component
public class SkillEventListener {

        @Autowired
        private PontuationService pontuationService;

        @Autowired
        private InternService internService;

        @Autowired
        private JobPostingService jobPostingService;

        @Autowired
        private InscriptionsJobPostingRepository inscriptionsJobPostRepository;

        @EventListener
        public void handleSkillVagaAlterada(JobPostingSkillChangedEvent event) {
                Long jobPostingId = event.getJobPostingId();
                try {
                        System.out.println("Evento de skill de vaga alterada recebido");
                        var jobPost = jobPostingService.findById(jobPostingId);

                        List<InscriptionsJobPostingModel> inscricoes = inscriptionsJobPostRepository
                                        .findAllByJobPostingId(jobPost);

                        for (InscriptionsJobPostingModel inscricao : inscricoes) {

                                var intern = internService.getInternByUser(inscricao.getUserId());
                                Double novaPontuacao = pontuationService.calculatePontuation(intern,
                                                inscricao.getJobPostingId());
                                inscricao.setPontuation(novaPontuacao);
                                inscriptionsJobPostRepository.save(inscricao);
                        }
                } catch (Exception e) {
                        System.err.println("Erro ao processar evento JobPostingSkillChangedEvent: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        @EventListener
        public void handleSkillEstagiarioAlterada(InternSkillChangedEvent event) {
                Long userId = event.getUserId();

                try {
                        var intern = internService.getInternById(userId);

                        List<InscriptionsJobPostingModel> inscricoes = inscriptionsJobPostRepository
                                        .findAllByUserIdAndJobPostingStatus(intern.getUserId(), Status.ACTIVE);

                        for (InscriptionsJobPostingModel inscricao : inscricoes) {

                                Double novaPontuacao = pontuationService.calculatePontuation(intern,
                                                inscricao.getJobPostingId());
                                inscricao.setPontuation(novaPontuacao);
                                inscriptionsJobPostRepository.save(inscricao);
                        }
                } catch (Exception e) {
                        System.err.println("Erro ao processar evento InternSkillChangedEvent: " + e.getMessage());
                        e.printStackTrace();
                }
        }

}