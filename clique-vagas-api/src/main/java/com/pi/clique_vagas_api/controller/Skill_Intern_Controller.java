package com.pi.clique_vagas_api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.resources.dto.skill.Intern.Skill_Intern_Dto;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;

@RestController
@RequestMapping("/skill_intern")
public class Skill_Intern_Controller {

    @Autowired
    private Skill_Intern_Service skillInternService;

    @PostMapping
    public ResponseEntity<Long> addSkillIntern(@RequestBody Skill_Intern_Dto body) {
        var skillInternId = skillInternService.createSkillIntern(body);
        return ResponseEntity.created(URI.create("/skill/" + skillInternId.getId())).build();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<List<Skill_Intern_Model>>
    // getAllSkillsInternById(@RequestBody Long idIntern) {
    // var skillsIntern = skillInternService.getAllSkillsInternById(idIntern);
    // return ResponseEntity.ok(skillsIntern);
    // }

    @DeleteMapping
    public ResponseEntity<Void> deleteSkillIntern(@RequestBody Long id) {
        skillInternService.deleteSkillIntern(id);
        return ResponseEntity.ok().build();
    }
}
