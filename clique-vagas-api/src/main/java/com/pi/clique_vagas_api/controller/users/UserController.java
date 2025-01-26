package com.pi.clique_vagas_api.controller.users;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pi.clique_vagas_api.resources.dto.user.GetDataUserGeneric;
import com.pi.clique_vagas_api.resources.dto.user.UserDto;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.users.UserService;
import com.pi.clique_vagas_api.service.users.typeUsers.CompanyService;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InternService internService;

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserDto body) {
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long userId) {
        var user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
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

    @PutMapping
    public ResponseEntity<Void> updateUserById(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserDto body) {

        var user = userService.findByEmail(userDetails.getUsername());
        userService.updateUserById(user, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
