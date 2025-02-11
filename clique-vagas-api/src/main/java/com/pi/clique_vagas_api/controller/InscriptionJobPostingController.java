package com.pi.clique_vagas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.service.inscriptionsJobPost.InscriptionsJobPostingService;

@RestController
@RequestMapping("/inscriptionJobPosting")
public class InscriptionJobPostingController {

    @Autowired
    private InscriptionsJobPostingService inscriptionJobPostingService;

    @PostMapping
    public void createInscription() {
        inscriptionJobPostingService.save(null, null);
    }

}
