package com.skilllance;

import com.skilllance.dto.AuthResponse;
import com.skilllance.entity.User;
import com.skilllance.repository.UserRepository;
import com.skilllance.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void registerShouldCreateUserAndGenerateToken() {

        String email = "test-" + UUID.randomUUID() + "@skilllance.com";

        User user = new User(
                "Test User",
                email,
                "TestPassword123",
                "FREELANCER"
        );

        AuthResponse response = authService.register(user);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isBlank());

        assertNotNull(response.getUser());
        assertEquals(email, response.getUser().getEmail());
        assertEquals("FREELANCER", response.getUser().getRole());

        User savedUser = userRepository.findByEmail(email)
                .orElseThrow();

        assertNotEquals(
                "TestPassword123",
                savedUser.getPassword()
        );

        assertTrue(
                passwordEncoder.matches(
                        "TestPassword123",
                        savedUser.getPassword()
                )
        );
    }

    @Test
    void loginShouldReturnTokenForCorrectPassword() {

        String email = "login-" + UUID.randomUUID() + "@skilllance.com";

        User user = new User(
                "Login User",
                email,
                "LoginPassword123",
                "CLIENT"
        );

        authService.register(user);

        AuthResponse response = authService.login(
                email,
                "LoginPassword123"
        );

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isBlank());

        assertNotNull(response.getUser());
        assertEquals(email, response.getUser().getEmail());
        assertEquals("CLIENT", response.getUser().getRole());

        assertEquals(
                3,
                response.getToken().split("\\.").length
        );
    }

    @Test
    void loginShouldRejectWrongPassword() {

        String email = "wrong-password-" +
                UUID.randomUUID() +
                "@skilllance.com";

        User user = new User(
                "Security User",
                email,
                "CorrectPassword123",
                "FREELANCER"
        );

        authService.register(user);

        assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(
                        email,
                        "WrongPassword123"
                )
        );
    }
}
