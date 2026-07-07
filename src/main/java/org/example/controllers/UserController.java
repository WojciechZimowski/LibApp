package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.User;
import org.example.services.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userService;
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        return userService.findUserByName(authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
