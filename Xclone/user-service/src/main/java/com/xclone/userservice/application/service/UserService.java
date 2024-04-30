package com.xclone.userservice.application.service;

import com.xclone.userservice.responseDto.UserDetailsResponseDto;

import java.util.UUID;

public interface UserService {
    public UserDetailsResponseDto getUserById(UUID id);
}
