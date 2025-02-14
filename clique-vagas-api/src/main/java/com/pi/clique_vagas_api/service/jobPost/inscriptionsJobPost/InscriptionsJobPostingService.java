package com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.jobPost.InscriptionsJobPostingModel;
import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.jobPosting.InscriptionsJobPostingRepository;
import com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost.GetInscriptionJobPostWithIdDto;
import com.pi.clique_vagas_api.resources.dto.user.GetNameAndEmailUserDto;
import com.pi.clique_vagas_api.resources.enums.Status;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

@Service
public class InscriptionsJobPostingService {

    @Autowired
    private InscriptionsJobPostingRepository inscriptionsJobPostRepository;

    public InscriptionsJobPostingModel save(JobPostingModel jobPostingModel, UserModel userModel, Double pontuation) {

        if (inscriptionsJobPostRepository.findByUserIdAndJobPostingId(userModel, jobPostingModel).isPresent()) {
            throw new RuntimeException("User already inscribed in this job post");
        }

        if (jobPostingModel.getJobPostingStatus() == Status.INACTIVE) {
            throw new RuntimeException("Error: Job post is inactive");
        }

        InscriptionsJobPostingModel inscriptionsJobPost = new InscriptionsJobPostingModel();
        inscriptionsJobPost.setJobPostingId(jobPostingModel);
        inscriptionsJobPost.setUserId(userModel);
        inscriptionsJobPost.setPontuation(pontuation);
        inscriptionsJobPost.setInscriptionDate(DateUtils.nowInZone());

        return inscriptionsJobPostRepository.save(inscriptionsJobPost);
    }

    public void delete(Long InscriptionId, UserModel user) {

        var inscription = findById(InscriptionId);

        if (inscription.getJobPostingId().getJobPostingStatus() == Status.INACTIVE) {
            throw new RuntimeException("Error: Job post is inactive");
        }

        inscriptionsJobPostRepository.delete(inscription);
    }

    public InscriptionsJobPostingModel findById(Long id) {
        return inscriptionsJobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription not found"));
    }

    public InscriptionsJobPostingModel findByUserIdAndJobPostingId(UserModel user, JobPostingModel jobPostingModel) {
        return inscriptionsJobPostRepository.findByUserIdAndJobPostingId(user, jobPostingModel)
                .orElseThrow(() -> new RuntimeException("Inscription not found"));
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

    public List<GetInscriptionJobPostWithIdDto> findAllByUserIdAndJobPostingId(UserModel userId,
            JobPostingModel jobPostingId) {
        return inscriptionsJobPostRepository.findAllByUserIdAndJobPostingId(userId, jobPostingId)
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
