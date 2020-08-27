package com.vend.gateway.notification.error.type;

import org.springframework.http.HttpStatus;

public interface MessagingErrorType {
    int getCode();

    String getErrorCode();

    String getErrorMessage();
}
