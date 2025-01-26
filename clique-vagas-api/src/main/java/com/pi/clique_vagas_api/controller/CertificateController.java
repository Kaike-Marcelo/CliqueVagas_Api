package com.pi.clique_vagas_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.CertificateModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateDto;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.service.CertificateService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserService userService;

    @Autowired
    private InternService internService;

    @PostMapping
    public ResponseEntity<Long> uploadCertificate(
            @ModelAttribute @Validated CertificateDto certificateDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        UserModel user = userService.findByEmail(userDetails.getUsername());
        InternModel intern = internService.getInternByIdUser(user);
        CertificateModel savedCertificate = certificateService.save(certificateDto, intern);
        return ResponseEntity.ok(savedCertificate.getId());
    }

    @GetMapping
    public ResponseEntity<List<CertificateWithIdDto>> getCertificateByInternId(
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findByEmail(userDetails.getUsername());
        var intern = internService.getInternByIdUser(user);
        var certificate = certificateService.getCertificatesByInternId(intern);
        return ResponseEntity.ok(certificate);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCertificate(@RequestParam Long certificateId,
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findByEmail(userDetails.getUsername());
        var intern = internService.getInternByIdUser(user);

        certificateService.deleteCertificate(certificateId, intern);
        return ResponseEntity.noContent().build();
    }

}
