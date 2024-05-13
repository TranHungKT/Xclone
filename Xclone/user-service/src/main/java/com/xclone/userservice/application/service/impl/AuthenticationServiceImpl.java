package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.AuthenticationService;
import com.xclone.userservice.application.service.JwtService;
import com.xclone.userservice.application.service.MailSenderService;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.ActiveUserRequest;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.requestDto.ResetPasswordRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    @Value("${hostname}")
    private String hostname;

    @Override
    public LoginResponseDto login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return LoginResponseDto.convertToLoginResponseDto(jwtService.generateToken(request.getEmail()));
    }

    @Override
    @Transactional
    public void registration(RegistrationRequest request) throws MessagingException {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw ErrorHelper.buildBadRequestException("Authentication", "Email already exists");
        }

        User user = User.from(request, passwordEncoder);
        userRepository.save(user);

        String subject = "Activation code";
        String template = "registration-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("fullName", user.getFullName());
        attributes.put("registrationUrl", "http://" + hostname + "/email/" + user.getEmail() + "/activate/" + user.getActivationCode());

        mailSenderService.sendHtmlEmail(template, user.getEmail(), attributes, subject);
    }

    @Override
    @Transactional
    public void activateUser(ActiveUserRequest request) {
        var user = userRepository.findByEmailAndActivationCode(request.getEmail(), request.getActiveUserCode())
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("Bad request", "Wrong email or active code"));

        user.setActivationCode(null);
        user.setConfirmed(true);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
            var user = userRepository.findUserByEmail(email).orElseThrow(() -> ErrorHelper.buildBadRequestException("Bad request", "User email do not exist"));

            String passwordResetCode = UUID.randomUUID().toString();
            user.setPasswordResetCode(passwordResetCode);
            userRepository.save(user);

            String subject = "Reset password";
            String template = "password-reset-template";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("fullName", user.getFullName());
            attributes.put("resetUrl", "http://" + hostname + "/email/" + user.getEmail() + "/reset/" + user.getPasswordResetCode());
            mailSenderService.sendHtmlEmail(template, user.getEmail(), attributes, subject);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request){
        var user = userRepository.findByEmailAndPasswordResetCode(request.getEmail(), request.getResetPasswordCode())
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("Bad request", "User do not exist"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordResetCode(null);
        userRepository.save(user);
    }

}
