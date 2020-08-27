package com.vend.gateway.notification.messaging.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSMessageRequest implements Serializable {
    private String toPhoneNumber;
    private String smsTemplateWithPlaceHolders;
    private Map<String, String> smsDataValues;
}
