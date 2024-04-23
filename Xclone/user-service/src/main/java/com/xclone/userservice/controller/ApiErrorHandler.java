package com.xclone.userservice.controller;

import com.xclone.userservice.error.ApiError;
import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.error.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
@Order(3)
public class ApiErrorHandler {
    public static final String ERROR_LOCATION_HEADER = "header";
    public static final String ERROR_LOCATION_QUERY = "query";
    public static final String ERROR_LOCATION_BODY = "body";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(exception, request, HttpStatus.BAD_REQUEST));
    }

    private ApiError buildErrorResponse(@NonNull Throwable exception, HttpServletRequest request, HttpStatus status) {
        if (exception instanceof ServiceException) {
            return ApiError.builder()
                    .errorId(((ServiceException) exception).getErrorId())
                    .message(exception.getMessage())
                    .details(((ServiceException) exception).getErrorDetails()
                            .stream()
                            .map(apiErrorDetails -> ApiErrorDetails.builder()
                                    .field(apiErrorDetails.getField())
                                    .issue(apiErrorDetails.getIssue())
                                    .location(apiErrorDetails.getLocation())
                                    .build()
                            ).toList()
                    )
                    .httpStatus(status)
                    .build();
        }

        return ApiError.builder()
                .errorId(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .details(exception.getCause() == null ? null
                                : List.of(
                                ApiErrorDetails.builder()
                                        .issue(exception.getCause().getMessage())
                                        .location(ERROR_LOCATION_BODY)
                                        .build()
                        )
                )
                .build();
    }
}
