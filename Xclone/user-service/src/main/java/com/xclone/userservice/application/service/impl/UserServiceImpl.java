package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.UserService;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.configuration.security.SecurityUserDetails;
import com.xclone.userservice.repository.db.dao.UserImageRepository;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.repository.db.entity.UserImage;
import com.xclone.userservice.requestDto.UpdateUserDetailsRequest;
import com.xclone.userservice.responseDto.UserDetailsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    @Override
    public UserDetailsResponseDto getUserById(UUID id) {
        final var user = userRepository
                .findByUserId(id)
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("userID", String.format("Can not find user with id %s", id.toString()), id.toString()));

        return UserDetailsResponseDto.convertToUserDetailsResponseDto(user);
    }

    @Override
    public void updateUserDetails(UUID userId, UpdateUserDetailsRequest request) {
        var cloneUser = userRepository.findByUserId(userId).orElseThrow(() -> ErrorHelper.buildBadRequestException("userId", "user not exist"));
        cloneUser.setWebsite(request.getWebsite());
        cloneUser.setUsername(request.getUserName());
        cloneUser.setLocation(request.getLocation());
        cloneUser.setAbout(request.getAbout());
        cloneUser.setFullName(request.getFullName());
        if (Objects.nonNull(request.getUserImageId())) {
            var userImage = userImageRepository.findAllByImageId(request.getUserImageId()).orElseThrow(() -> ErrorHelper.buildBadRequestException("imageId", "image not exist"));
            cloneUser.setUserImage(userImage);
            userImage.setUser(cloneUser);
        }
        userRepository.save(cloneUser);
    }

    public static User getUser() {
        SecurityUserDetails principal = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }
}
