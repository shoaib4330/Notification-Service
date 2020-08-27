package com.vend.gateway.notification.messaging.fcm.rest.response.success;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FcmPushNotificationResponseMessage implements Serializable {
    private static final long serialVersionUID = -1L;
    private String name;
}
