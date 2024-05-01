package com.xclone.userservice.application.service;

import java.util.Map;

public interface JwtService {
    Map<String, String> extractUserData(String token);
    String extractUserDataByField(String token, String fieldName);
    String generateToken(String email);
    boolean isTokenValid(String token);
}
