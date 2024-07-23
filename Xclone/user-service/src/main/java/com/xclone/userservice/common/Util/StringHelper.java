package com.xclone.userservice.common.Util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringHelper {
    private static final String HASHTAG_PATTERN = "#(\\w+)";
    private static final Pattern pattern = Pattern.compile(HASHTAG_PATTERN);

    public static List<String> extractHashtags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile(HASHTAG_PATTERN);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            hashtags.add(matcher.group(1));
        }
        return hashtags;
    }

}
