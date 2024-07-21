package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFollowingStatusRequest {
    @NotBlank(message = "Action can not be empty")
    private String action;
}
