package com.vend.gateway.notification.messaging.fcm.mapper;

import com.vend.gateway.notification.messaging.fcm.dto.FcmPushNotificationMessage;
import com.vend.gateway.notification.messaging.fcm.dto.Message;
import com.vend.gateway.notification.messaging.fcm.dto.Notification;
import com.vend.gateway.notification.messaging.fcm.dto.PushNotificationRequestMessage;
import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PushNotificationMessageMapper {

  @Autowired private TemplateDataMapper templateDataMapper;

  public FcmPushNotificationMessage map(
      PushNotificationRequestMessage pushNotificationRequestMessage) {

    return FcmPushNotificationMessage.builder()
        .message(
            Message.builder()
                .token(pushNotificationRequestMessage.getFcmRegistrationId())
                .notification(createNotification(pushNotificationRequestMessage))
                .data(
                    pushNotificationRequestMessage.getAdditionalData() != null
                        ? pushNotificationRequestMessage.getAdditionalData()
                        : Collections.emptyMap())
                .data(
                    "title",
                    templateDataMapper.mapDataToPlaceHolders(
                        pushNotificationRequestMessage.getTitleTemplateWithPlaceHolders(),
                        pushNotificationRequestMessage.getPlaceHolderDataForTitle()))
                .data(
                    "body",
                    templateDataMapper.mapDataToPlaceHolders(
                        pushNotificationRequestMessage.getBodyTemplateWithPlaceHolders(),
                        pushNotificationRequestMessage.getPlaceHolderDataForBody()))
                .build())
        .build();
  }

  private Notification createNotification(
      PushNotificationRequestMessage pushNotificationRequestMessage) {
    return Notification.builder()
        .title(
            templateDataMapper.mapDataToPlaceHolders(
                pushNotificationRequestMessage.getTitleTemplateWithPlaceHolders(),
                pushNotificationRequestMessage.getPlaceHolderDataForTitle()))
        .body(
            templateDataMapper.mapDataToPlaceHolders(
                pushNotificationRequestMessage.getBodyTemplateWithPlaceHolders(),
                pushNotificationRequestMessage.getPlaceHolderDataForBody()))
        .build();
  }
}
