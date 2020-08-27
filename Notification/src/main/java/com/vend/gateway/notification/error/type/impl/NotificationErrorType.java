package com.vend.gateway.notification.error.type.impl;

import com.vend.gateway.notification.error.type.ApiErrorType;
import com.vend.gateway.notification.error.type.MessagingErrorType;
import org.springframework.http.HttpStatus;

public enum NotificationErrorType implements ApiErrorType {
    INTERNAL_SERVER_ERROR(7000, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
    FCM_ERROR_RESPONSE(7001, "Error response received as result of FCM call", HttpStatus.INTERNAL_SERVER_ERROR),
    UNABLE_TO_READ_FCM_KEY(7002, "Could not read FCM Key file", HttpStatus.INTERNAL_SERVER_ERROR);

    private static final String PREFIX = "VEND-ERROR-";
    private HttpStatus httpStatusCode;
    private int code;
    private String message;

    NotificationErrorType(int code, String message, HttpStatus httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getErrorCode() {
        return PREFIX + this.code;
    }

    @Override
    public String getErrorMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
