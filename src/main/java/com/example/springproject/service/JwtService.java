package com.example.springproject.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    /**
     * generates a jwt to authenticate logged-in users
     * @param email
     * @return jwtToken
     */
    public String generateToken(String email) {

        Instant now = Instant.now();
        // valid for 24 hours
        Instant expiration = now.plus(24, ChronoUnit.HOURS);

        // generate token
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * extracts email from jwt Token
     * @param token
     * @return String email
     */
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload() // get data
                .getSubject();
    }
}
