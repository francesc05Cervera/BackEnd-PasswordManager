package com.francesco.passwordmanager.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.francesco.passwordmanager.entity.Utente;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTservice {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    public String generateToken(Utente userDetails) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractMail(String token) {
        return extractClaim(normalizeToken(token), Claims::getSubject);
    }

    public boolean isTokenValid(String token, Utente userDetails) {
        try {
                return userDetails != null
                    && extractMail(token).equals(userDetails.getEmail())
                    && !isTokenExpired(normalizeToken(token));
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String normalizeToken(String token) {
        if (token == null || token.isBlank()) {
            return "";
        }
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
