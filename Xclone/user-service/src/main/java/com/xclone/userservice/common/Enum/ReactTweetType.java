package com.xclone.userservice.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum ReactTweetType {
    LIKE("LIKE"),
    DISLIKE("DISLIKE");

    private final String value;

    public static Set<String> getSetOfReactTweetType() {
        return Set.of(LIKE.value, DISLIKE.value);
    }
}
