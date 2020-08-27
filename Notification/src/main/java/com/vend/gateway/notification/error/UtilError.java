package com.vend.gateway.notification.error;

import com.vend.gateway.notification.error.type.MessagingErrorType;
import org.springframework.http.HttpStatus;

public enum UtilError implements MessagingErrorType {

    ERROR_CONVERTING_TO_JSON(2000, "Error converting to json string");

    private static final String PREFIX = "VDMESSAGING-UTIL-";
    private int code;
    private String message;

    UtilError(int code, String message) {
        this.code = code;
        this.message = message;
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
    public int getCode() {
        return this.code;
    }
}
