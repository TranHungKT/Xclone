package com.xclone.userservice.requestDto;

import com.xclone.userservice.common.Interface.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class ResetPasswordRequest {
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @NotBlank(message = "Confirm password can not be empty")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String confirmPassword;

    @NotBlank(message = "Reset password code can not be empty")
    private String resetPasswordCode;
}
