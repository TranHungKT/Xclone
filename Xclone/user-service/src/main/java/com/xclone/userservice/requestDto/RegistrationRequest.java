package com.xclone.userservice.requestDto;

import com.xclone.userservice.common.Interface.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@PasswordMatches
public class RegistrationRequest {
    @NotBlank(message = "Full name can not be empty")
    private String fullName;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @NotBlank(message = "Confirmed password can not be empty")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String confirmPassword;
}
