package com.pi.clique_vagas_api.controller.users.typeUsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.InternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.PostInternDto;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/intern")
public class InternController {

    @Autowired
    private InternService internService;

    @PostMapping
    @RolesAllowed("INTERN")
    public ResponseEntity<Long> createIntern(@RequestBody CreateInternDto body) {
        var intern = internService.createIntern(body);
        return ResponseEntity.ok(intern.getId());
    }

    @GetMapping
    public ResponseEntity<InternDto> getIntern(@AuthenticationPrincipal UserDetails userDetails) {
        var intern = internService.getInternByEmailDto(userDetails.getUsername());
        return ResponseEntity.ok(intern);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InternDto>> getAllInterns() {
        var interns = internService.getAllInterns();
        return ResponseEntity.ok(interns);
    }

    @PutMapping
    public ResponseEntity<Void> updateIntern(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PostInternDto body) {
        internService.updateIntern(userDetails.getUsername(), body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIntern(@AuthenticationPrincipal UserDetails userDetails) {
        internService.deleteIntern(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
