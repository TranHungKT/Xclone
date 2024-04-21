package com.xclone.userservice.error;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class ServiceException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -4106480316239009712L;

    private final String  errorId;
    private final transient List<ApiErrorDetails> errorDetails;

    public ServiceException(String errorId, String msg, List<ApiErrorDetails> errorDetails){
        super(msg);
        this.errorDetails = errorDetails;
        this.errorId = errorId;
    }
}
