package com.vend.gateway.notification.messaging.sms;

import com.vend.gateway.notification.messaging.sms.dto.SMSMessageRequest;

public interface SMSService<T> {
    T sendSMS(SMSMessageRequest smsMessageRequest);
}
