package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactTweetRequest {
    @NotBlank(message = "React type can not be empty")
    private String reactType;
}
