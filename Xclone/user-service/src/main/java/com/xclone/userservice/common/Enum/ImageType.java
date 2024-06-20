package com.xclone.userservice.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum ImageType {
    TWEET_IMAGE_TYPE("TWEET_IMAGE_TYPE"),
    USER_IMAGE_TYPE("USER_IMAGE_TYPE");

    private final String value;

    public static Set<String> getSetOfImageType() {
        return Set.of(TWEET_IMAGE_TYPE.value, USER_IMAGE_TYPE.value);
    }
}
