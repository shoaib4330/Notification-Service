package com.vend.gateway.notification.messaging.mail.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MailMessageRequest {
    private String from;
    private List<To> to;
    private String subject;
    private To cc;
    private To bcc;
    private String emailTemplateWithPlaceHolders;
    private Map<String, String> emailDataValues;
}
