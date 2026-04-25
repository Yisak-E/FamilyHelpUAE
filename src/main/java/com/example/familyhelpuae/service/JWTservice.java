package com.example.familyhelpuae.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTservice {
    // Requirement: Secure communication. Use a key at least 256-bit long.
    private static final String SECRET_STRING = "YourSuperSecretKeyThatMustBeAtLeast32CharactersLong!!";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // .setSubject() changed to .subject()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(SECRET_KEY) // Algorithm is now inferred from the key type
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY) // .setSigningKey() changed to .verifyWith()
                .build()
                .parseSignedClaims(token) // .parseClaimsJws() changed to .parseSignedClaims()
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}