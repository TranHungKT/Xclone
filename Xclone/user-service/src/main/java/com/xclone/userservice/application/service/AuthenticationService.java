package com.xclone.userservice.application.service;

import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.ActiveUserRequest;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.requestDto.ResetPasswordRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface AuthenticationService {
    LoginResponseDto login(AuthenticationRequest request);

    void registration(RegistrationRequest request) throws MessagingException;

    void activateUser(ActiveUserRequest request);

    void forgotPassword(String email) throws MessagingException;

    void resetPassword(ResetPasswordRequest request);
}
