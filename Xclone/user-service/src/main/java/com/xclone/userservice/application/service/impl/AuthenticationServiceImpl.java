package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.AuthenticationService;
import com.xclone.userservice.application.service.JwtService;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import com.xclone.userservice.responseDto.LoginResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public LoginResponseDto login(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> ErrorHelper.buildBadRequestException("Authentication", "Email or password is wrong"));

        return LoginResponseDto.convertToLoginResponseDto(jwtService.generateToken(user));
    }

    @Override
    @Transactional
    public void registration(RegistrationRequest request){
        if(userRepository.existsUserByEmail(request.getEmail())){
           throw ErrorHelper.buildBadRequestException("Authentication", "Email already exists");
        }

        User user = User.from(request, passwordEncoder);
        userRepository.save(user);
    }
}
