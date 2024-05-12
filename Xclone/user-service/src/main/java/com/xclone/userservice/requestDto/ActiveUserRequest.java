package com.xclone.userservice.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveUserRequest {
    private String email;
    private String activeUserCode;
}
