package com.xclone.userservice.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum FollowingUserAction {
    FOLLOW("FOLLOW"),
    UNFOLLOW("UNFOLLOW");

    private final String value;

    public static Set<String> getSetOfFollowingUserAction() {
        return Set.of(FOLLOW.value, UNFOLLOW.value);
    }
}
