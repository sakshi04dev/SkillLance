package com.skilllance.service;

import com.skilllance.entity.User;
import com.skilllance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        validateUser(user);

        String email = normalizeEmail(user.getEmail());
        String role = normalizeRole(user.getRole());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        user.setName(user.getName().trim());
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return userRepository.findByEmail(
                normalizeEmail(email)
        );
    }

    public User updateUser(Long id, User updatedUser) {

        if (updatedUser == null) {
            throw new IllegalArgumentException(
                    "User details are required"
            );
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        if (updatedUser.getName() == null
                || updatedUser.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Name is required"
            );
        }

        if (updatedUser.getEmail() == null
                || updatedUser.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        if (updatedUser.getRole() == null
                || updatedUser.getRole().isBlank()) {
            throw new IllegalArgumentException(
                    "Role is required"
            );
        }

        String email = normalizeEmail(updatedUser.getEmail());
        String role = normalizeRole(updatedUser.getRole());

        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    if (!user.getId().equals(existingUser.getId())) {
                        throw new IllegalArgumentException(
                                "Email is already registered"
                        );
                    }
                });

        existingUser.setName(
                updatedUser.getName().trim()
        );
        existingUser.setEmail(email);
        existingUser.setRole(role);

        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {

            if (updatedUser.getPassword().length() < 8) {
                throw new IllegalArgumentException(
                        "Password must be at least 8 characters"
                );
            }

            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        userRepository.deleteById(id);
    }

    private void validateUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User details are required"
            );
        }

        if (user.getName() == null
                || user.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Name is required"
            );
        }

        if (user.getEmail() == null
                || user.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        if (user.getPassword() == null
                || user.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        if (user.getPassword().length() < 8) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters"
            );
        }

        if (user.getRole() == null
                || user.getRole().isBlank()) {
            throw new IllegalArgumentException(
                    "Role is required"
            );
        }
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizeRole(String role) {

        String normalizedRole = role.trim().toUpperCase();

        if (!normalizedRole.equals("CLIENT")
                && !normalizedRole.equals("FREELANCER")) {
            throw new IllegalArgumentException(
                    "Role must be CLIENT or FREELANCER"
            );
        }

        return normalizedRole;
    }
}