package com.pi.clique_vagas_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.skills.SkillModel;
import com.pi.clique_vagas_api.resources.dto.skill.CreateSkillDto;
import com.pi.clique_vagas_api.service.skills.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillModel> addSkill(@RequestBody CreateSkillDto body) {
        var skillId = skillService.add(body);
        return ResponseEntity.created(URI.create("/skill/" + skillId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<List<SkillModel>> getAllSkills() {
        var skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSkill(@RequestBody Long id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
