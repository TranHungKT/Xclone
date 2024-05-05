package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.AuthenticationService;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/email/{email}/activate/{activateCode}")
    public ResponseEntity<Void> activateUser(@PathVariable String email, @PathVariable String activateCode) throws MessagingException {
        // TODO: VALIDATION INPUT
        authenticationService.activateUser(email, activateCode);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
