package com.pi.clique_vagas_api.service.users.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGeneric;
import com.pi.clique_vagas_api.resources.enums.UserRole;

@Service
public class ProfileService {

    @Autowired
    private InternProfileService internProfileService;

    @Autowired
    private CompanyProfileService companyProfileService;

    public GetDataUserGeneric getUserProfileByRole(UserModel user) {
        if (user.getRole() == UserRole.INTERN) {
            return internProfileService.getDataByIdUser(user);
        } else if (user.getRole() == UserRole.COMPANY) {
            return companyProfileService.getDataByIdUser(user);
        } else {
            throw new EventNotFoundException("Profile not found for this role");
        }
    }

}
