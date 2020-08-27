package com.vend.gateway.notification.error;

import com.vend.gateway.notification.error.type.MessagingErrorType;

public enum SMSErrorType implements MessagingErrorType {
  SMS_REQUEST_NULL(2000, "SMS request payload cannot be null"),
  NO_RECIPIENT_SPECIFIED(2001, "No recipients provided for the SMS"),
  SMS_TEMPLATE_INVALID(2002, "SMS text template is null or empty"),
  INVALID_PHONE_NUMBER(2003, "Invalid phone number"),
  ;

  private static final String PREFIX = "VDMESSAGING-";
  private int code;
  private String message;

  SMSErrorType(int code, String message) {
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
