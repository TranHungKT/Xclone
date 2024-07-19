package com.xclone.userservice.application.service;

import com.xclone.userservice.requestDto.UpdateFollowingStatusRequest;
import com.xclone.userservice.requestDto.UpdateUserDetailsRequest;
import com.xclone.userservice.responseDto.UserDetailsResponseDto;

import java.util.UUID;

public interface UserService {
    UserDetailsResponseDto getUserById(UUID id);

    void updateUserDetails(UUID userId, UpdateUserDetailsRequest request);

    void updateFollowingUserStatus(UUID userId, UpdateFollowingStatusRequest request);
}
