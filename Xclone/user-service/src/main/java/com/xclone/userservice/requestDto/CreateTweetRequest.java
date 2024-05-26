package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTweetRequest {
    @NotBlank(message = "Text can not be empty")
    @NotNull(message = "Text can not be null")
    private String text;
}
