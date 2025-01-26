package com.pi.clique_vagas_api.controller.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.resources.dto.user.company.CreateCompanyDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Long> createCompany(@RequestBody CreateCompanyDto body) {

        if (body.getUser().role() != UserRole.COMPANY)
            throw new EventNotFoundException("User is not a company");

        var user = userService.createUser(body.getUser());
        addressService.createAddress(body.getAddress(), user);
        var company = companyService.createCompany(body.getCompany(), user);

        return ResponseEntity.ok(company.getId());
    }

    @GetMapping
    public ResponseEntity<List<CompanyModel>> getAllCompanies() {
        var companies = companyService.getAllCompany();
        return ResponseEntity.ok(companies);
    }
}
