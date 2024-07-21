package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.UserService;
import com.xclone.userservice.common.Enum.FollowingUserAction;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.configuration.security.SecurityUserDetails;
import com.xclone.userservice.repository.db.dao.UserImageRepository;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.UpdateFollowingStatusRequest;
import com.xclone.userservice.requestDto.UpdateUserDetailsRequest;
import com.xclone.userservice.responseDto.UserDetailsResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    @Override
    public UserDetailsResponseDto getUserById(UUID id) {
        final var user = userRepository
                .findByUserId(id)
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("userID", String.format("Can not find user with id %s", id.toString()), id.toString()));

        var userImage = userImageRepository.findByUserId(user.getUserId());

        return UserDetailsResponseDto.convertToUserDetailsResponseDto(user, userImage.orElse(null));
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
            userImage.setUser(cloneUser);
        }
        userRepository.save(cloneUser);
    }

    @Override
    @Transactional
    public void updateFollowingUserStatus(UUID userId, UpdateFollowingStatusRequest request) {
        if (!FollowingUserAction.getSetOfFollowingUserAction().contains(request.getAction())) {
            throw ErrorHelper.buildBadRequestException("action", "action is not valid");
        }
        var currentUserId = getUser().getUserId();

        var currentUserAndUserToUpdateFollowingStatus = userRepository.findUsersByUserIdIn(Set.of(currentUserId, userId)).stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
        var currentUser = currentUserAndUserToUpdateFollowingStatus.get(currentUserId);
        var userToUpdateFollowingStatus = currentUserAndUserToUpdateFollowingStatus.get(userId);
        if (Objects.isNull(currentUser) || Objects.isNull(userToUpdateFollowingStatus)) {
            throw ErrorHelper.buildBadRequestException("User", "User is not exist");
        }

        if (Objects.equals(request.getAction(), FollowingUserAction.FOLLOW.getValue())) {
            userToUpdateFollowingStatus.getFollowers().add(currentUser);
        } else {
            userToUpdateFollowingStatus.getFollowers().remove(currentUser);
        }

        userRepository.save(userToUpdateFollowingStatus);
    }

    public static User getUser() {
        SecurityUserDetails principal = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.user();
    }
}
