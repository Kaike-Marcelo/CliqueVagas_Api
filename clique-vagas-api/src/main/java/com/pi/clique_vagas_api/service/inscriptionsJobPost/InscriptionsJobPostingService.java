package com.pi.clique_vagas_api.service.inscriptionsJobPost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.model.JobPostingModel;
import com.pi.clique_vagas_api.repository.InscriptionsJobPostingRepository;
import com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost.GetInscriptionJobPostWithIdDto;
import com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost.InscriptionsJobPostDto;
import com.pi.clique_vagas_api.resources.dto.user.GetNameAndEmailUserDto;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

@Service
public class InscriptionsJobPostingService {

    @Autowired
    private InscriptionsJobPostingRepository inscriptionsJobPostRepository;

    public InscriptionsJobPostingModel save(InscriptionsJobPostDto inscription, Double pontuation) {
        InscriptionsJobPostingModel inscriptionsJobPost = new InscriptionsJobPostingModel();
        inscriptionsJobPost.setJobPostingId(inscription.jobPostingId());
        inscriptionsJobPost.setUserId(inscription.userId());
        inscriptionsJobPost.setPontuation(pontuation);
        inscriptionsJobPost.setInscriptionDate(DateUtils.nowInZone());

        return inscriptionsJobPostRepository.save(inscriptionsJobPost);
    }

    public void delete(InscriptionsJobPostingModel inscriptionsJobPost) {
        inscriptionsJobPostRepository.delete(inscriptionsJobPost);
    }

    public List<GetInscriptionJobPostWithIdDto> findAllByJobPosting(JobPostingModel jobPosting) {
        return inscriptionsJobPostRepository.findAllByJobPostingId(jobPosting)
                .stream()
                .map(inscription -> new GetInscriptionJobPostWithIdDto(
                        inscription.getId(),
                        new GetNameAndEmailUserDto(
                                inscription.getUserId().getFirstName(),
                                inscription.getUserId().getLastName(),
                                inscription.getUserId().getEmail(),
                                FileUtils.loadFileFromPath(inscription.getUserId().getUrlImageProfile())),
                        inscription.getPontuation(),
                        inscription.getInscriptionDate()))
                .toList();
    }

}
