package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.UserService;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.responseDto.UserDetailsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsResponseDto getUserById(UUID id) {
        final var user = userRepository
                .findUserById(id)
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("userID", String.format("Can not find user with id %s", id.toString()), id.toString()));

        return UserDetailsResponseDto.convertToUserDetailsResponseDto(user);
    }
}