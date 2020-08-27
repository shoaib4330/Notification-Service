package com.vend.gateway.notification.error.exception;

import com.vend.gateway.notification.error.type.MessagingErrorType;

public class VendException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    protected final String appCode;
    protected final String appMessage;

    public VendException(String errorCode, String errorMessage, Exception e) {
        super(e);
        appMessage = errorMessage;
        appCode = errorCode;
    }

    public VendException(String errorCode, String errorMessage) {
        super(errorMessage);
        appMessage = errorMessage;
        appCode = errorCode;
    }

    public VendException(MessagingErrorType messagingErrorType, Exception e) {
        super(e);
        appMessage = messagingErrorType.getErrorMessage();
        appCode = messagingErrorType.getErrorCode();
    }

    public VendException(MessagingErrorType messagingErrorType) {
        super(messagingErrorType.getErrorMessage());
        appMessage = messagingErrorType.getErrorMessage();
        appCode = messagingErrorType.getErrorCode();
    }

    public String getAppCode() {
        return this.appCode;
    }

    public String getAppMessage() {
        return this.appMessage;
    }
}
