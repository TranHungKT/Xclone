package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @NotBlank(message = "Email can not be empty")
    private String email;

    private String password;

    private String confirmPassword;

    private String resetPasswordCode;
}
