package com.xclone.userservice.repository.db.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class EntityHelper {
    public static final String USER_TABLE = "users";
    public static final String TWEET_TABLE = "tweet";
    public static final String TAG_TABLE = "tags";
    public static final String TWEET_TAG_TABLE = "tweet_tag";

    public static final String TWEET_IMAGE_TABLE = "tweet_image";
    public static final String USER_IMAGE_TABLE = "user_image";
    public static final String TWEET_LIKES_TABLE = "tweet_likes";
    public static final String RETWEETS_TABLE = "retweets";
    public static final String USER_SUBSCRIPTIONS = "user_subscriptions";
}
