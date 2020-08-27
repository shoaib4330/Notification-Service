package com.vend.gateway.notification.common.exception;

import com.vend.gateway.notification.error.exception.ErrorResponse;
import com.vend.gateway.notification.error.exception.RemoteVendException;
import com.vend.gateway.notification.error.exception.VendException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class VendExceptionHandler {

  @ExceptionHandler(RemoteVendException.class)
  public ResponseEntity<Object> handleException(RemoteVendException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .errorCode(exception.getAppCode()) //
            .message(exception.getAppMessage()) //
            .httpStatus(exception.getHttpStatus())
            .build();
    return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
  }

  @ExceptionHandler(VendException.class)
  public ResponseEntity<Object> handleException(VendException exception) {
    ErrorResponse errorResponse =
            ErrorResponse.builder()
                    .errorCode(exception.getAppCode()) //
                    .message(exception.getAppMessage()) //
                    .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleException(RuntimeException exception) {
    ErrorResponse errorResponse =
            ErrorResponse.builder()
                    .message("Something went wrong") //
                    .operationMessage(String.format("Cause: %s, Message: %s", exception.getCause(), exception.getMessage()))
                    .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
