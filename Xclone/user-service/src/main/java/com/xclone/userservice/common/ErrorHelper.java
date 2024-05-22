package com.xclone.userservice.common;

import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import jakarta.mail.MessagingException;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Collections;

@UtilityClass
public final class ErrorHelper {
    public static final String ERROR_LOCATION = "query";

    public static BadRequestException buildBadRequestException(String fieldName, String issue, String value) {
        return new BadRequestException(
                Collections.singletonList(ApiErrorDetails.builder()
                        .field(fieldName)
                        .issue(issue)
                        .value(value)
                        .location(ERROR_LOCATION)
                        .build())
        );
    }

    public static BadRequestException buildBadRequestException(String fieldName, String issue) {
        return new BadRequestException(
                Collections.singletonList(ApiErrorDetails.builder()
                        .field(fieldName)
                        .issue(issue)
                        .location(ERROR_LOCATION)
                        .build())
        );
    }

    public static MessagingException buildMessagingException(String issue) {
        return new MessagingException(issue);
    }
    public static BadCredentialsException buildBadCredentialsException(String issue) {
        return new BadCredentialsException(issue);
    }
}
