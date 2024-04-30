package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.JwtService;
import com.xclone.userservice.repository.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";

    @Override
    public Map<String, String> extractUserData(String token) {
        var claims = extractAllClaims(token);
        return Map.of(
                "email", claims.get("email", String.class),
                "fullName", claims.get("fullName", String.class)
        );
    }

    @Override
    public String extractUserDataByField(String token, String fieldName) {
        var claims = extractAllClaims(token);
        return claims.get(fieldName, String.class);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails user) {
        return true;
    }

    @Override
    public String generateToken(User user) {
        long timestamp = System.currentTimeMillis();

        Claims claims = Jwts.claims()
                .add("fullName", user.getFullName())
                .add("email", user.getEmail())
                .build();

        return Jwts.builder()
                .expiration(new Date(timestamp + 2 * 60 * 60 * 1000))
                .claims(claims)
                .signWith(getSingingKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
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
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
