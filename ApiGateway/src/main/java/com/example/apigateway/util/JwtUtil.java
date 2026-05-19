package com.example.apigateway.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

        private final String SECRET_KEY = "supersecretkey123456supersecretkey123456"; // 32+ chars

        private Key getSigningKey() {
            return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        public String extractUserId(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
    }


