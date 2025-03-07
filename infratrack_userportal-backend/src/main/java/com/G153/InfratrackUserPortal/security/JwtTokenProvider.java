package com.G153.InfratrackUserPortal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.io.Decoders;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 2 seconds
    private long jwtExpiration;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String idNumber) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(idNumber)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getIdNumberFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date now = new Date();
            if (claims.getExpiration().before(now) || claims.getExpiration().equals(now)) {
                System.out.println("Token expired at: " + claims.getExpiration());
                System.out.println("Current time: " + now);
                return false;
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Token validation failed: " + ex.getMessage());
            return false;
        }
    }

    public Map<String, Object> decodeToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Map.of(
                "idNumber", claims.getSubject(),
                "issuedAt", claims.getIssuedAt(),
                "expiration", claims.getExpiration()
        );
    }
    
}