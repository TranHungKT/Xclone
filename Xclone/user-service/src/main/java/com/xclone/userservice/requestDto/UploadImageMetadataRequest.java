package com.xclone.userservice.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadImageMetadataRequest {
    @NotBlank(message = "Type can not be empty")
    private String type;
}
