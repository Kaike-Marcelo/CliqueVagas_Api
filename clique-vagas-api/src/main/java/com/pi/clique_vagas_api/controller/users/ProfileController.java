package com.pi.clique_vagas_api.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.user.GetUserWithAddressDto;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.profile.ProfileService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @GetMapping
    @RolesAllowed({ "INTERN", "COMPANY" })
    public ResponseEntity<GetUserWithAddressDto> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var data = profileService.getUserProfileByRole(user);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{email}")
    @RolesAllowed({ "COMPANY" })
    public ResponseEntity<GetUserWithAddressDto> getProfileUser(@PathVariable String email) {

        var user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var data = profileService.getUserProfileByRole(user);

        return ResponseEntity.ok(data);
    }

}
