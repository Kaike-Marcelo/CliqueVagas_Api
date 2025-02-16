package com.pi.clique_vagas_api.service.users.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.user.GetUserWithAddressDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.users.UserService;

@Service
public class ProfileService {

    @Autowired 
    private UserService userService;

    @Autowired
    private InternProfileService internProfileService;

    @Autowired
    private CompanyProfileService companyProfileService;

    public GetUserWithAddressDto getUserProfileByRole(UserModel user) {
        var userDto = userService.getObjUserDto(user);

        if (user.getRole() == UserRole.INTERN) {
            return internProfileService.getDataByIdUser(user, userDto);
        } else if (user.getRole() == UserRole.COMPANY) {
            return companyProfileService.getDataByIdUser(user, userDto);
        } else {
            throw new EventNotFoundException("Profile not found for this role");
        }
    }

}
