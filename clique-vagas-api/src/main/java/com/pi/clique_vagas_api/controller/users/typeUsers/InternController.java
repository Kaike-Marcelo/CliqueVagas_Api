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

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/intern")
public class InternController {

    @Autowired
    private InternService internService;

    @PostMapping
    public ResponseEntity<Long> createIntern(@RequestBody CreateInternDto body) {

        if (body.getUser().role() != UserRole.INTERN)
            throw new EventNotFoundException("User is not an intern");
        var intern = internService.createIntern(body);

        return ResponseEntity.ok(intern.getId());
    }

    @GetMapping
    public ResponseEntity<List<InternModel>> getAllInterns() {
        var interns = internService.getAllInterns();
        return ResponseEntity.ok(interns);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIntern(@AuthenticationPrincipal UserDetails userDetails) {
        internService.deleteIntern(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
