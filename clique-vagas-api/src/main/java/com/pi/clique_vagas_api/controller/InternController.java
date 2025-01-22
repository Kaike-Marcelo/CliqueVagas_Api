package com.pi.clique_vagas_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.user.intern.CreateInternDto;
import com.pi.clique_vagas_api.resources.dto.user.intern.GetInternDto;
import com.pi.clique_vagas_api.service.users.InternService;

@RestController
@RequestMapping("/intern")
public class InternController {

    @Autowired
    private InternService internService;

    @PostMapping
    public ResponseEntity<Long> createIntern(@RequestBody CreateInternDto intern) {
        var userId = internService.createIntern(intern);
        return ResponseEntity.ok(userId.getId());
    }

    @GetMapping
    public ResponseEntity<List<InternModel>> getAllInterns() {
        var interns = internService.getAllInterns();
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetInternDto> getInternByIdUser(@PathVariable("id") Long userId) {
        var intern = internService.getDataByIdUser(userId);
        if (intern == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(intern);
    }

}
