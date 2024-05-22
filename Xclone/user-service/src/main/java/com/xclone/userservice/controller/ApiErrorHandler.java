package com.xclone.userservice.controller;

import com.xclone.userservice.error.ApiError;
import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.error.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ApiErrorHandler {
    public static final String ERROR_LOCATION_HEADER = "header";
    public static final String ERROR_LOCATION_QUERY = "query";
    public static final String ERROR_LOCATION_BODY = "body";

    @ExceptionHandler({BadRequestException.class, MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> handleBadRequestException(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(exception, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialException(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(exception, request, HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleBindingException(BindException ex, HttpServletRequest request) {
        List<ApiErrorDetails> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            var fieldObject = ((FieldError) error).getRejectedValue();
            String fieldValue = Objects.isNull(fieldObject) ? "" : fieldObject.toString();
            String errorMessage = error.getDefaultMessage();
            errors.add(
                    ApiErrorDetails.builder()
                            .field(fieldName)
                            .issue(errorMessage)
                            .value(fieldValue)
                            .location(ERROR_LOCATION_QUERY)
                            .build()
            );
        });
        var badRequestException = new BadRequestException(errors);
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(badRequestException, request, HttpStatus.BAD_REQUEST));
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
