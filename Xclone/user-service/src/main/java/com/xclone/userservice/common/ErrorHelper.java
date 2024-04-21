package com.xclone.userservice.common;

import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import lombok.experimental.UtilityClass;

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

}
