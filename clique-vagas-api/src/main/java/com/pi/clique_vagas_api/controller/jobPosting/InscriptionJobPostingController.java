package com.pi.clique_vagas_api.controller.jobPosting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.inscriptionsJobPost.GetInscriptionJobPostWithIdDto;
import com.pi.clique_vagas_api.resources.dto.jobPost.JobPostWithIdDto;
import com.pi.clique_vagas_api.service.jobPost.JobPostingService;
import com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost.InscriptionsJobPostingService;
import com.pi.clique_vagas_api.service.jobPost.inscriptionsJobPost.PontuationService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/inscriptionJobPosting")
public class InscriptionJobPostingController {

    @Autowired
    private InscriptionsJobPostingService inscriptionJobPostingService;

    @Autowired
    private UserService userService;

    @Autowired
    private InternService internService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private PontuationService pontuationService;

    @PostMapping("/{jobPostingId}")
    public ResponseEntity<Long> saveInscriptionJobPostign(@PathVariable Long jobPostingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var post = jobPostingService.findById(jobPostingId);
        var intern = internService.getInternByUser(user);

        var pontuation = pontuationService.calculatePontuation(intern, post);

        var inscription = inscriptionJobPostingService.save(post, user, pontuation);
        return ResponseEntity.ok(inscription.getId());
    }

    @GetMapping("company/{jobPostingId}")
    public ResponseEntity<List<GetInscriptionJobPostWithIdDto>> getInscriptionsJobPosting(
            @PathVariable Long jobPostingId, @AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findByEmail(userDetails.getUsername());
        var company = companyService.getCompanyByUser(user);
        var post = jobPostingService.findByIdAndCompanyId(jobPostingId, company);
        return ResponseEntity.ok(inscriptionJobPostingService.findAllByJobPosting(post));
    }

    @GetMapping("intern/check/{jobPostingId}")
    public ResponseEntity<Boolean> chckInscriptionInternInJobPosting(@PathVariable Long jobPostingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var post = jobPostingService.findById(jobPostingId);
        return ResponseEntity.ok(inscriptionJobPostingService.checkInscription(user, post));
    }

    @GetMapping("/intern")
    public ResponseEntity<List<JobPostWithIdDto>> getInscriptionsIntern(
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(inscriptionJobPostingService.findAllByUserId(user));
    }

    @DeleteMapping("/{jobPostingId}")
    public ResponseEntity<Void> deleteInscriptionJobPosting(@PathVariable Long jobPostingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        var post = jobPostingService.findById(jobPostingId);
        inscriptionJobPostingService.delete(post, user);
        return ResponseEntity.ok().build();
    }

}
