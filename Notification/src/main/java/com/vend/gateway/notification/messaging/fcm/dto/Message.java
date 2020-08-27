package com.vend.gateway.notification.messaging.fcm.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = -1L;
    private String token;
    private Notification notification;
    private Map<String, String> data;

    @Builder
    public Message(
            Notification notification, String token, @Singular("data") Map<String, String> data) {
        this.notification = notification;
        this.token = token;
        this.data = data;
    }
}
