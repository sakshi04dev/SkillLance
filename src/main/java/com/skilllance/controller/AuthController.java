package com.skilllance.controller;

import com.skilllance.dto.AuthResponse;
import com.skilllance.dto.LoginRequest;
import com.skilllance.dto.RegisterRequest;
import com.skilllance.entity.User;
import com.skilllance.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {

        return ResponseEntity.ok(
                authService.login(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
    }
}