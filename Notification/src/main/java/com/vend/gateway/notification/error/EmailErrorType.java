package com.vend.gateway.notification.error;

import com.vend.gateway.notification.error.type.MessagingErrorType;

public enum EmailErrorType implements MessagingErrorType {
  EMAIL_INVALID(1000, "Email is invalid"),
  MAIL_REQUEST_NULL(1001, "Mail request payload cannot be null"),
  NO_RECIPIENT_SPECIFIED(1002, "No recipients provided for the email"),
  ERROR_CREATING_RECIPIENT(1003, "Error creating email recipient"),
  MAIL_FROM_ATTRIBUTE_INVALID(1004, "Email's (From) attribute is invalid");

  private static final String PREFIX = "VDMESSAGING-";
  private int code;
  private String message;

  EmailErrorType(int code, String message) {
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
