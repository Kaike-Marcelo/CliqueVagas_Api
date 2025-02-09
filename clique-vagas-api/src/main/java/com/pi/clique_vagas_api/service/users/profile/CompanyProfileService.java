package com.pi.clique_vagas_api.service.users.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.resources.dto.user.company.PostCompanyDto;
import com.pi.clique_vagas_api.resources.dto.user.company.CompanyProfileDto;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;

@Service
public class CompanyProfileService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public CompanyProfileDto getDataByIdUser(UserModel user) {

        var company = companyService.getCompanyByUser(user);
        var address = addressService.getAddressDtoByUserId(user);

        var companyDto = getObjCompanyDto(company);

        CompanyProfileDto companyProfile = new CompanyProfileDto();
        companyProfile.setUser(user);
        companyProfile.setAddress(address);
        companyProfile.setCompany(companyDto);

        return companyProfile;
    }

    public PostCompanyDto getObjCompanyDto(CompanyModel company) {
        return new PostCompanyDto(
                company.getCompanyName(),
                company.getCnpj(),
                company.getTelephoneResponsible(),
                company.getSectorOfOperation(),
                company.getWebsiteLink());
    }

}
