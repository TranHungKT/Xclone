package com.xclone.userservice.requestDto;

import com.xclone.userservice.repository.db.entity.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CreateTweetRequest {
    @NotBlank(message = "Text can not be empty")
    @NotNull(message = "Text can not be null")
    private String text;

    private Set<UUID> imageIds;
}
