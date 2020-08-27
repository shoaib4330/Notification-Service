package com.vend.gateway.notification.messaging.fcm.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FcmPushNotificationMessage implements Serializable {
    private static final long serialVersionUID = -1L;
    private Message message;
}
