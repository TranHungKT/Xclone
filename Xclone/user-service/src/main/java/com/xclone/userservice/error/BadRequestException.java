package com.xclone.userservice.error;

import java.util.List;

public class BadRequestException extends ServiceException {
    private static final long serialVersionUID = -674027687192987343L;
    private static final String ERROR_ID = "XCLONE_API_ERROR_INVALID_REQUEST";
    private static final String MESSAGE = "Invalid request - user service";

    public BadRequestException(List<ApiErrorDetails> errorDetails) {
        super(ERROR_ID, MESSAGE, errorDetails);
    }
}
