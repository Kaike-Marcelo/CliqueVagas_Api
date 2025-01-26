package com.pi.clique_vagas_api.controller.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternProfileDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.AddressService;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/intern")
public class InternController {

    @Autowired
    private InternService internService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Long> createIntern(@RequestBody CreateInternDto body) {

        if (body.getUser().role() != UserRole.INTERN)
            throw new EventNotFoundException("User is not an intern");

        var user = userService.createUser(body.getUser());
        addressService.createAddress(body.getAddress(), user);
        var intern = internService.createIntern(body.getIntern(), user);

        return ResponseEntity.ok(intern.getId());
    }

    @GetMapping
    public ResponseEntity<List<InternModel>> getAllInterns() {
        var interns = internService.getAllInterns();
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/profile")
    public ResponseEntity<InternProfileDto> getInternByIdUser(@AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findByEmail(userDetails.getUsername());

        var intern = internService.getDataByIdUser(user);
        if (intern == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(intern);
    }
}
