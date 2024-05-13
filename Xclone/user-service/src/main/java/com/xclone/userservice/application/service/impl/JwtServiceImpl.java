package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private String jwtSecret = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";

    @Override
    public Map<String, String> extractUserData(String token) {
        var claims = extractAllClaims(token);
        return Map.of(
                "email", claims.get("email", String.class)
        );
    }

    @Override
    public String extractUserDataByField(String token, String fieldName) {
        var claims = extractAllClaims(token);
        return claims.get(fieldName, String.class);
    }

    @Override
    public boolean isTokenValid(String token) {
        return isTokenExpired(token);
    }

    @Override
    public String generateToken(String email) {
        long timestamp = System.currentTimeMillis();
        long expirationTime = 30L * 24 * 60 * 60 * 1000;

        Claims claims = Jwts.claims()
                .add("email", email)
                .build();

        return Jwts.builder()
                .expiration(new Date(timestamp + expirationTime))
                .claims(claims)
                .signWith(getSingingKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        var a = extractClaim(token, Claims::getExpiration);
        System.out.println(a);
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSingingKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private SecretKey getSingingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
