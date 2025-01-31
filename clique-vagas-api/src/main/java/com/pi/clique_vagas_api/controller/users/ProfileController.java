package com.pi.clique_vagas_api.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGeneric;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private InternService internService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<GetDataUserGeneric> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        GetDataUserGeneric data = null;

        var user = userService.findByEmail(userDetails.getUsername());

        if (user.getRole() == UserRole.INTERN) {
            data = internService.getDataByIdUser(user);
        }

        if (user.getRole() == UserRole.COMPANY) {
            data = companyService.getDataByIdUser(user);
        }

        return ResponseEntity.ok(data);
    }

}
