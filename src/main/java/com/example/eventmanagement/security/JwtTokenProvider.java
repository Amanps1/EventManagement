package com.example.eventmanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration; // in ms

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ✅ Generate token with roles
    public String generateToken(String email,String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(email)
                .claim("roles",List.of(role))// email / username
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Get username/email
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Get roles from token
    public List<String> getRolesFromToken(String token) {
        return getClaims(token).get("roles", List.class);
    }

    // ✅ Validate token
    public boolean validateToken(String token) {
        try {
            getClaims(token); // will throw if invalid
            return true;
        } catch (Exception ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
            return false;
        }
    }

    // ✅ Utility: extract claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
