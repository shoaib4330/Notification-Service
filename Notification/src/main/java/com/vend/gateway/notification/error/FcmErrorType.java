package com.vend.gateway.notification.error;

import com.vend.gateway.notification.error.type.MessagingErrorType;

public enum FcmErrorType implements MessagingErrorType {

    FCM_REQUEST_NULL(3000, "FCM request payload cannot be null"),
    NO_FCM_TOKEN(3001, "FCM token/Fcm registration id must be provided"),
    TITLE_TEMPLATE_INVALID(3002, "FCM title template is null or empty"),
    BODY_TEMPLATE_INVALID(3003, "FCM body template is null or empty"),
    ;

    private static final String PREFIX = "VDMESSAGING-";
    private int code;
    private String message;

    FcmErrorType(int code, String message) {
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
