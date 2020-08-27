package com.vend.gateway.notification.error.type;

import org.springframework.http.HttpStatus;

public interface ApiErrorType extends MessagingErrorType {
  HttpStatus getHttpStatusCode();
}
