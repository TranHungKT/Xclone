package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUserDetailsRequest {
    @NotBlank(message = "Full name can not be empty")
    private String fullName;
    @NotBlank(message = "User name can not be empty")
    private String userName;
    private String location;
    private String about;
    private String website;
    private UUID userImageId;
}
