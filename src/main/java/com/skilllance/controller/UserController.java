package com.skilllance.controller;

import com.skilllance.dto.UpdateUserRequest;
import com.skilllance.dto.UserDTO;
import com.skilllance.entity.User;
import com.skilllance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody User user
    ) {
        User createdUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToDTO(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id
    ) {
        return userService.getUserById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email
    ) {
        return userService.getUserByEmail(email)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        User existingUser = userService.getUserById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        User updatedUser = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                existingUser.getRole()
        );

        User savedUser = userService.updateUser(
                id,
                updatedUser
        );

        return ResponseEntity.ok(
                convertToDTO(savedUser)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}