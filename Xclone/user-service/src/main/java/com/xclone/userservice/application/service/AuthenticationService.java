package com.xclone.userservice.application.service;

import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto login(AuthenticationRequest request);
    void registration(RegistrationRequest request);
}
