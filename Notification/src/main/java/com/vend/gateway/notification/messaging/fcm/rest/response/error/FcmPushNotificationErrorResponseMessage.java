package com.vend.gateway.notification.messaging.fcm.rest.response.error;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FcmPushNotificationErrorResponseMessage implements Serializable {
    private static final long serialVersionUID = -1L;
    private Error error;
}
