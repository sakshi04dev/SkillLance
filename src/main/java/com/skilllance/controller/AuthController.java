package com.skilllance.controller;

import com.skilllance.dto.UserDTO;
import com.skilllance.entity.User;
import com.skilllance.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(authService.register(user));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            return ResponseEntity.ok(
                    authService.login(email, password)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
