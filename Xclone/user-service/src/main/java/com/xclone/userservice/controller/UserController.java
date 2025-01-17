package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.UserService;
import com.xclone.userservice.requestDto.UpdateFollowingStatusRequest;
import com.xclone.userservice.requestDto.UpdateUserDetailsRequest;
import com.xclone.userservice.responseDto.UserDetailsResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(
            @PathVariable @Valid final UUID id
    ) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserDetails(
            @PathVariable @Valid final UUID id,
            @RequestBody @Valid UpdateUserDetailsRequest request
    ) {
        userService.updateUserDetails(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("follow-action/{id}")
    public ResponseEntity<Void> updateFollowUser(
            @PathVariable @Valid final UUID id,
            @RequestBody @Valid UpdateFollowingStatusRequest request
    ) {
        userService.updateFollowingUserStatus(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/followers")
    public ResponseEntity<List<UserDetailsResponseDto>> getUserFollowers(
            @PathVariable @Valid final UUID id
    ) {
        return new ResponseEntity<>(userService.getUserFollowers(id), HttpStatus.OK);
    }

    @GetMapping("{id}/followings")
    public ResponseEntity<List<UserDetailsResponseDto>> getUserFollowing(
            @PathVariable @Valid final UUID id
    ) {
        return new ResponseEntity<>(userService.getUserFollowings(id), HttpStatus.OK);
    }
}
