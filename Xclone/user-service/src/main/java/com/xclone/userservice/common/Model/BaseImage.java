package com.xclone.userservice.common.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class BaseImage {
    private String src;
    private UUID id;
}
