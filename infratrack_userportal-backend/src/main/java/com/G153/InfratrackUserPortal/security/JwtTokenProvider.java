package com.G153.InfratrackUserPortal.security;

import com.G153.InfratrackUserPortal.DTO.UserProfileDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.io.Decoders;
import java.util.Map;

/**
 * Component for generating and validating JWT tokens.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 2 seconds
    private long jwtExpiration;

    /**
     * Retrieves the signing key for JWT.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given ID number.
     *
     * @param idNumber the ID number
     * @return the generated JWT token
     */
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

    /**
     * Extracts the ID number from the given JWT token.
     *
     * @param token the JWT token
     * @return the ID number
     */
    public String getIdNumberFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
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

    /**
     * Decodes the given JWT token and extracts its claims.
     *
     * @param token the JWT token
     * @return a map containing the token claims
     */
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

    /**
     * Extracts the user ID from the given JWT token.
     *
     * @param token the JWT token
     * @return the user ID
     */
    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}