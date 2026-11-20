package com.skilllance.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:86400000}") long expirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
        this.expirationTime = expirationTime;
    }

    public String generateToken(String email, String role) {

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + expirationTime
        );

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {

        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    public boolean isTokenValid(String token) {

        try {
            extractAllClaims(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
