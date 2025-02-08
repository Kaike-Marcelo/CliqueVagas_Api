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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.resources.dto.user.PostUserDto;
import com.pi.clique_vagas_api.service.users.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody PostUserDto body) {
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    @PostMapping("/photo")
    public ResponseEntity<String> savePhotoProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("file") MultipartFile file) {

        var user = userService.findByEmail(userDetails.getUsername());
        var image = userService.savePhotoProfile(file, user.getUserId());
        return ResponseEntity.ok(image.getUrlImageProfile());
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

    @GetMapping("/photo")
    public ResponseEntity<byte[]> getPhotoProfile(@AuthenticationPrincipal UserDetails userDetails) {
        var image = userService.getPhotoProfileByEmail(userDetails.getUsername());
        return ResponseEntity.ok(image);
    }

    @PutMapping
    public ResponseEntity<Void> updateUserById(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PostUserDto body) {

        var user = userService.findByEmail(userDetails.getUsername());
        userService.updateUserById(user, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/photo")
    public ResponseEntity<Void> deletePhotoProfile(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deletePhotoProfile(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
