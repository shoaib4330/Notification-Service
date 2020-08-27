package com.vend.gateway.notification.messaging.mail.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class To implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private Map<String, String> additionalData;
}
