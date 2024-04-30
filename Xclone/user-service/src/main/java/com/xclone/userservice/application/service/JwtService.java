package com.xclone.userservice.application.service;

import com.xclone.userservice.repository.db.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    Map<String, String> extractUserData(String token);
    String extractUserDataByField(String token, String fieldName);
    String generateToken(User user);
    boolean isTokenValid(String token, UserDetails user);
}
