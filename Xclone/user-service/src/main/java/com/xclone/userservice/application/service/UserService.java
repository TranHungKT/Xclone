package com.xclone.userservice.application.service;

import com.xclone.userservice.responseDto.UserDetailsResponseDto;

import java.util.UUID;

public interface UserService {
    UserDetailsResponseDto getUserById(UUID id);
}
