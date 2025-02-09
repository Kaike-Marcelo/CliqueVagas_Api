package com.pi.clique_vagas_api.controller.skills;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_Dto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.service.skills.SkillService;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/skill_intern")
public class Skill_Intern_Controller {

    @Autowired
    private Skill_Intern_Service skillInternService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private InternService internService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Long> addSkillIntern(@RequestBody Skill_Intermediate_Dto body,
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var intern = internService.getInternByUser(user);
        var skill = skillService.getSkillById(body.getIdSkill());

        var skillIntern = skillInternService.createSkillIntern(skill, intern, body);

        return ResponseEntity.status(HttpStatus.CREATED).body(skillIntern.getId());
    }

    @PutMapping
    public ResponseEntity<Void> updateSkillIntern(@RequestBody Skill_Intermediate_WithIdDto body,
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var intern = internService.getInternByUser(user);

        skillInternService.updateSkillIntern(body, intern);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Skill_Intermediate_WithIdDto>> getAllSkillsInternById(
            @AuthenticationPrincipal UserDetails userDetails) {

        var user = (UserModel) userService.findByEmail(userDetails.getUsername());
        var intern = internService.getInternByUser(user);

        var skillsIntern = skillInternService.getSkillsDtoByInternId(intern);
        return ResponseEntity.ok(skillsIntern);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkillIntern(@PathVariable Long id) {
        skillInternService.deleteSkillIntern(id);
        return ResponseEntity.ok().build();
    }
}
