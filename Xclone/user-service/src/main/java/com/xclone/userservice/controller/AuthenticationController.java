package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.AuthenticationService;
import com.xclone.userservice.requestDto.ActiveUserRequest;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.ForgotPasswordRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.requestDto.ResetPasswordRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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

//    @InitBinder({
//        "authenticationRequest"
//    })
//    public void initBinders(WebDataBinder binder){
//       var validators = List.of(
//               new LoginValidator()
//       );
//
//       binder.replaceValidators(validators
//               .stream()
//               .filter(validator -> validator.supports(Objects.requireNonNull(binder.getTarget()).getClass()))
//               .toList()
//               .toArray(Validator[]::new)
//       );
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) throws MessagingException {
        authenticationService.registration(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/active-user")
    public ResponseEntity<String> activateUser(@Valid @RequestBody ActiveUserRequest body) {
        authenticationService.activateUser(body);

        return ResponseEntity.ok("Active user success");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest body) throws MessagingException {
        authenticationService.forgotPassword(body.getEmail());
        return ResponseEntity.ok("Reset password code is send to your E-mail");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ResetPasswordRequest body) {
        authenticationService.resetPassword(body);
        return ResponseEntity.ok("Reset password success");
    }
}
