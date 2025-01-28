package com.pi.clique_vagas_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.repository.InscriptionsJobPostingRepository;
import com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost.InscriptionsJobPostDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class InscriptionsJobPostingService {

    @Autowired
    private InscriptionsJobPostingRepository inscriptionsJobPostRepository;

    public InscriptionsJobPostingModel save(InscriptionsJobPostDto inscription) {
        InscriptionsJobPostingModel inscriptionsJobPost = new InscriptionsJobPostingModel();
        inscriptionsJobPost.setJobPostingId(inscription.jobPostingId());
        inscriptionsJobPost.setInternId(inscription.internId());
        inscriptionsJobPost.setPontuation(0);
        inscriptionsJobPost.setInscriptionDate(DateUtils.nowInZone());
        return inscriptionsJobPostRepository.save(inscriptionsJobPost);
    }

    public void delete(InscriptionsJobPostingModel inscriptionsJobPost) {
        inscriptionsJobPostRepository.delete(inscriptionsJobPost);
    }

}
