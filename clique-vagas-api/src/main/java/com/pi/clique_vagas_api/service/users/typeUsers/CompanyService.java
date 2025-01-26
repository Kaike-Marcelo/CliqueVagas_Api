package com.pi.clique_vagas_api.service.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.repository.users.CompanyRepository;
import com.pi.clique_vagas_api.resources.dto.user.company.CompanyDto;
import com.pi.clique_vagas_api.resources.dto.user.company.CompanyProfileDto;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Transactional
    public CompanyModel createCompany(CompanyDto company, UserModel userModel) {
        var companyModel = new CompanyModel(
                null,
                userModel,
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
    public CompanyModel getCompanyByIdUser(UserModel user) {
        return companyRepository.findByUserId(user).orElseThrow(() -> new EventNotFoundException("Company not found"));
    }

    @Transactional
    public CompanyProfileDto getDataByIdUser(UserModel user) {

        var company = getCompanyByIdUser(user);
        var address = addressService.getAddressDtoByUserId(user);

        CompanyDto companyDto = new CompanyDto(
                company.getCompanyName(),
                company.getCnpj(),
                company.getTelephoneResponsible(),
                company.getSectorOfOperation(),
                company.getWebsiteLink());

        CompanyProfileDto companyProfile = new CompanyProfileDto();
        companyProfile.setUser(user);
        companyProfile.setAddress(address);
        companyProfile.setCompany(companyDto);

        return companyProfile;
    }

    @Transactional
    public void updateCompany(Long id, CompanyDto company) {
        var companyModel = getCompanyById(id);

        companyModel.setCompanyName(company.companyName());
        companyModel.setCnpj(company.cnpj());
        companyModel.setTelephoneResponsible(company.telephoneResponsible());
        companyModel.setSectorOfOperation(company.sectorOfOperation());
        companyModel.setWebsiteLink(company.websiteLink());
        companyModel.setUpdatedAt(DateUtils.nowInZone());

        companyRepository.save(companyModel);
    }

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
