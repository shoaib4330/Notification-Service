package com.vend.gateway.notification.error.exception;

import com.vend.gateway.notification.error.type.ApiErrorType;
import org.springframework.http.HttpStatus;

public class RemoteVendException extends VendException {

  protected final HttpStatus httpStatus;

  public RemoteVendException(ApiErrorType apiErrorType) {
    super(apiErrorType);
    this.httpStatus = apiErrorType.getHttpStatusCode();
  }

  public RemoteVendException(String errorCode, String errorMessage, HttpStatus status, Exception e) {
    super(errorCode, errorMessage, e);
    this.httpStatus = status;
  }

  public RemoteVendException(String errorCode, String errorMessage, HttpStatus status) {
    super(errorCode, errorMessage);
    this.httpStatus = status;
  }

  public HttpStatus getHttpStatus(){
      return this.httpStatus;
  }
}
