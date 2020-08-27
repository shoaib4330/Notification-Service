package com.vend.gateway.notification.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
    private String from;
    private String to;
    private String subject;
    private String emailTemplateWithPlaceHolders;
    private Map<String, String> emailDataValues;
}
