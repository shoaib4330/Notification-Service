package com.vend.gateway.notification.messaging.fcm.service;

import com.vend.gateway.notification.messaging.fcm.dto.FcmPushNotificationMessage;
import com.vend.gateway.notification.messaging.fcm.dto.PushNotificationRequestMessage;

public interface FcmPushNotificationService {
    void sendPushNotification(PushNotificationRequestMessage pushNotificationRequestMessage);
}
