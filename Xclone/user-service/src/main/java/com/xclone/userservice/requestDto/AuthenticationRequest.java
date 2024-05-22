package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password can not be empty")
    private String password;
}
