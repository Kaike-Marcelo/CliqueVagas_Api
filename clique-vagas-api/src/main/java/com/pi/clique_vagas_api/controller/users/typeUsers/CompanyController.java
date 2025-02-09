package com.pi.clique_vagas_api.controller.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.user.company.CompanyDto;
import com.pi.clique_vagas_api.resources.dto.user.company.CreateCompanyDto;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @RolesAllowed("COMPANY")
    public ResponseEntity<Long> createCompany(@RequestBody CreateCompanyDto body) {
        var company = companyService.createCompany(body);
        return ResponseEntity.ok(company.getId());
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        var companies = companyService.getAllCompany();
        return ResponseEntity.ok(companies);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompany(@AuthenticationPrincipal UserDetails userDetails) {
        companyService.deleteCompany(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
