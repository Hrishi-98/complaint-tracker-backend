package com.project.complaint_tracker.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key secretKey;
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        return Jwts.builder().setSubject(username)
                .setIssuedAt( new Date() )
                .setExpiration(new Date(System.currentTimeMillis()+1000 *60 * 60* 10)) // 10 hrs
                .signWith(secretKey).compact();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            validate(token); // Validates signature & expiration
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username);
        } catch (JwtException | SignatureException e) {
            return false;
        }
    }
    public void validate(String token) throws SignatureException {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        }
        catch(Exception je){
            throw new JwtException("Invalid Signature");
        }
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
