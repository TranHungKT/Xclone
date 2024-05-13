package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.AuthenticationService;
import com.xclone.userservice.requestDto.ActiveUserRequest;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.ForgotPasswordRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.requestDto.ResetPasswordRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthenticationRequest request) {
        // TODO: VALIDATION INPUT
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) throws MessagingException {
        // TODO: VALIDATION INPUT
        authenticationService.registration(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/active-user")
    public ResponseEntity<String> activateUser(@RequestBody ActiveUserRequest body) {
        // TODO: VALIDATION INPUT
        authenticationService.activateUser(body);

        return ResponseEntity.ok("Active user success");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest body) throws MessagingException {
            authenticationService.forgotPassword(body.getEmail());
            return ResponseEntity.ok("Reset password code is send to your E-mail");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ResetPasswordRequest body) {
        authenticationService.resetPassword(body);
        return ResponseEntity.ok("Reset password success");
    }
}
