package com.pi.clique_vagas_api.service.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.repository.users.CompanyRepository;
import com.pi.clique_vagas_api.resources.dto.user.company.PostCompanyDto;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.resources.dto.user.company.CreateCompanyDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public CompanyModel createCompany(CreateCompanyDto body) {

        if (companyRepository.findByCnpj(body.getCompany().cnpj()) != null)
            throw new EventNotFoundException("Company already exists with this CNPJ");

        var user = userService.createUser(body.getUser());
        addressService.createAddress(body.getAddress(), user);
        var company = body.getCompany();

        var companyModel = new CompanyModel(
                null,
                user,
                company.companyName(),
                company.cnpj(),
                company.telephoneResponsible(),
                company.sectorOfOperation(),
                company.websiteLink(),
                DateUtils.nowInZone(),
                null);
        return companyRepository.save(companyModel);
    }

    @Transactional
    public List<CompanyModel> getAllCompany() {
        return companyRepository.findAll();
    }

    @Transactional
    public CompanyModel getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Company not found"));
    }

    @Transactional
    public CompanyModel getCompanyByUser(UserModel user) {
        return companyRepository.findByUserId(user)
                .orElseThrow(() -> new EventNotFoundException("Company not found"));
    }

    @Transactional
    public CompanyModel updateCompany(Long id, PostCompanyDto company) {
        var companyModel = getCompanyById(id);

        companyModel.setCompanyName(company.companyName());
        companyModel.setCnpj(company.cnpj());
        companyModel.setTelephoneResponsible(company.telephoneResponsible());
        companyModel.setSectorOfOperation(company.sectorOfOperation());
        companyModel.setWebsiteLink(company.websiteLink());
        companyModel.setUpdatedAt(DateUtils.nowInZone());

        return companyRepository.save(companyModel);
    }

    @Transactional
    public void deleteCompany(String username) {
        var user = userService.findByEmail(username);
        if (user == null)
            throw new EventNotFoundException("User not found");
        companyRepository.deleteByUserId(user);
    }
}
