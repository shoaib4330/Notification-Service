package com.vend.gateway.notification.messaging.fcm.service.impl;

import com.vend.gateway.notification.autoconfig.fcm.FCMConfigurationProperties;
import com.vend.gateway.notification.error.FcmErrorType;
import com.vend.gateway.notification.error.exception.RemoteVendException;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.fcm.dto.FcmPushNotificationMessage;
import com.vend.gateway.notification.messaging.fcm.dto.PushNotificationRequestMessage;
import com.vend.gateway.notification.messaging.fcm.mapper.PushNotificationMessageMapper;
import com.vend.gateway.notification.messaging.fcm.rest.response.success.FcmPushNotificationResponseMessage;
import com.vend.gateway.notification.messaging.fcm.service.FcmPushNotificationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

public class FcmPushNotificationServiceImpl implements FcmPushNotificationService {

  private final Logger log = LoggerFactory.getLogger(FcmPushNotificationServiceImpl.class);

  @Autowired private RestTemplate fcmRestTemplate;

  @Autowired private PushNotificationMessageMapper pushNotificationMessageMapper;

  @Autowired private FCMConfigurationProperties fcmConfigurationProperties;

  @Override
  @Retryable(value = {RemoteVendException.class}, maxAttemptsExpression = "${vend.notification.max-retries}", backoff = @Backoff(delayExpression = "${vend.notification.retry-delay}"))
  public void sendPushNotification(PushNotificationRequestMessage pushNotificationRequestMessage) {
    validatePushNotificationRequestMessage(pushNotificationRequestMessage);
    FcmPushNotificationMessage fcmPushNotificationMessage =
        pushNotificationMessageMapper.map(pushNotificationRequestMessage);
    sendFcmPushNotification(fcmPushNotificationMessage);
  }

  private boolean sendFcmPushNotification(FcmPushNotificationMessage fcmPushNotificationMessage) {
    FcmPushNotificationResponseMessage response =
        fcmRestTemplate.postForObject(
            fcmConfigurationProperties.getGoogleFcmUrl(),
            fcmPushNotificationMessage,
            FcmPushNotificationResponseMessage.class);
    log.info("Successfully sent FCM push notification, response received : {}", response);
    return response != null && response.getName().length() > 0;
  }

  private void validatePushNotificationRequestMessage(
      PushNotificationRequestMessage pushNotificationRequestMessage) {
    if (pushNotificationRequestMessage == null)
      throw new VendException(FcmErrorType.FCM_REQUEST_NULL);
    if (StringUtils.isEmpty(pushNotificationRequestMessage.getFcmRegistrationId()))
      throw new VendException(FcmErrorType.NO_FCM_TOKEN);
    if (StringUtils.isEmpty(pushNotificationRequestMessage.getTitleTemplateWithPlaceHolders()))
      throw new VendException(FcmErrorType.TITLE_TEMPLATE_INVALID);
    if (StringUtils.isEmpty(pushNotificationRequestMessage.getBodyTemplateWithPlaceHolders()))
      throw new VendException(FcmErrorType.BODY_TEMPLATE_INVALID);
  }
}
