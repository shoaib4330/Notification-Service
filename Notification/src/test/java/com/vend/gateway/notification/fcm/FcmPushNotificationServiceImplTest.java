package com.vend.gateway.notification.fcm;

import com.google.common.collect.ImmutableMap;
import com.vend.gateway.notification.autoconfig.fcm.FCMConfigurationProperties;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.fcm.dto.FcmPushNotificationMessage;
import com.vend.gateway.notification.messaging.fcm.dto.PushNotificationRequestMessage;
import com.vend.gateway.notification.messaging.fcm.mapper.PushNotificationMessageMapper;
import com.vend.gateway.notification.messaging.fcm.rest.response.success.FcmPushNotificationResponseMessage;
import com.vend.gateway.notification.messaging.fcm.service.impl.FcmPushNotificationServiceImpl;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class FcmPushNotificationServiceImplTest {

  private static final String GOOGLE_FCM_URL = "https://fcm.googleapis.com/";
  private static final String FCM_DEIVCE_REGISTRATION_ID =
      "ugy24eThgfRM1MiF7iF7v45bvgyu42eThgfR45b1";
  private static final String FCM_NAME = "Shaloom";
  private static final String FCM_BODY_PARAM_TEST = "D4117";
  private static final String FCM_TITLE_TEMPLATE = "Hey Mr. ${NAME}, latest news coming in";
  private static final String FCM_BODY_TEMPLATE =
      "Latest news coming in regarding COVID-19, telling relevant antibody called ${BODY_PARAM_TEST} has been identified";
  private static final String FCM_TITLE_FORMED = "Hey Mr. Shaloom, latest news coming in";
  private static final String FCM_BODY_FORMED =
      "Latest news coming in regarding COVID-19, telling relevant antibody called D4117 has been identified";

  @InjectMocks FcmPushNotificationServiceImpl fcmPushNotificationService;
  @Mock private RestTemplate fcmRestTemplate;
  @Mock private FCMConfigurationProperties fcmConfigurationProperties;
  @Spy private PushNotificationMessageMapper pushNotificationMessageMapper;

  @Test
  public void testSendNotificationsWhenSuccess() {
    PushNotificationRequestMessage pushNotificationRequestMessage =
        PushNotificationRequestMessage.builder()
            .fcmRegistrationId(FCM_DEIVCE_REGISTRATION_ID)
            .titleTemplateWithPlaceHolders(FCM_TITLE_TEMPLATE)
            .bodyTemplateWithPlaceHolders(FCM_BODY_TEMPLATE)
            .placeHolderDataForTitle(ImmutableMap.<String, String>of("NAME", FCM_NAME))
            .placeHolderDataForBody(
                ImmutableMap.<String, String>of("BODY_PARAM_TEST", FCM_BODY_PARAM_TEST))
            .build();

    FcmPushNotificationMessage fcmPushNotificationMessage =
        pushNotificationMessageMapper.map(pushNotificationRequestMessage);

    Mockito.when(fcmConfigurationProperties.getGoogleFcmUrl()).thenReturn(GOOGLE_FCM_URL);
    Mockito.when(
            fcmRestTemplate.postForObject(
                GOOGLE_FCM_URL,
                fcmPushNotificationMessage,
                FcmPushNotificationResponseMessage.class)).thenReturn(null);

    fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);

    Mockito.verify(fcmConfigurationProperties, Mockito.times(1)).getGoogleFcmUrl();

    Mockito.verify(fcmRestTemplate, Mockito.times(1))
        .postForObject(
            GOOGLE_FCM_URL,
            pushNotificationMessageMapper.map(pushNotificationRequestMessage),
            FcmPushNotificationResponseMessage.class);
  }

  @Test(expected = VendException.class)
  public void testSendNotificationWhenFcmTokenIsEmpty() {
    PushNotificationRequestMessage pushNotificationRequestMessage =
        PushNotificationRequestMessage.builder()
            .fcmRegistrationId("")
            .titleTemplateWithPlaceHolders(FCM_TITLE_TEMPLATE)
            .bodyTemplateWithPlaceHolders(FCM_BODY_TEMPLATE)
            .placeHolderDataForTitle(ImmutableMap.<String, String>of("NAME", FCM_NAME))
            .placeHolderDataForBody(
                ImmutableMap.<String, String>of("BODY_PARAM_TEST", FCM_BODY_PARAM_TEST))
            .build();

    FcmPushNotificationMessage fcmPushNotificationMessage =
            pushNotificationMessageMapper.map(pushNotificationRequestMessage);

    fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);
  }

  @Test(expected = VendException.class)
  public void testSendNotificationWhenTitleTemplateIsEmpty() {
    PushNotificationRequestMessage pushNotificationRequestMessage =
            PushNotificationRequestMessage.builder()
                    .fcmRegistrationId(FCM_DEIVCE_REGISTRATION_ID)
                    .titleTemplateWithPlaceHolders("")
                    .bodyTemplateWithPlaceHolders(FCM_BODY_TEMPLATE)
                    .placeHolderDataForTitle(ImmutableMap.<String, String>of("NAME", FCM_NAME))
                    .placeHolderDataForBody(
                            ImmutableMap.<String, String>of("BODY_PARAM_TEST", FCM_BODY_PARAM_TEST))
                    .build();

    FcmPushNotificationMessage fcmPushNotificationMessage =
            pushNotificationMessageMapper.map(pushNotificationRequestMessage);

    fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);
  }

  @Test(expected = VendException.class)
  public void testSendNotificationWhenBodyTemplateIsEmpty() {
    PushNotificationRequestMessage pushNotificationRequestMessage =
            PushNotificationRequestMessage.builder()
                    .fcmRegistrationId(FCM_DEIVCE_REGISTRATION_ID)
                    .titleTemplateWithPlaceHolders(FCM_TITLE_TEMPLATE)
                    .bodyTemplateWithPlaceHolders("")
                    .placeHolderDataForTitle(ImmutableMap.<String, String>of("NAME", FCM_NAME))
                    .placeHolderDataForBody(
                            ImmutableMap.<String, String>of("BODY_PARAM_TEST", FCM_BODY_PARAM_TEST))
                    .build();

    FcmPushNotificationMessage fcmPushNotificationMessage =
            pushNotificationMessageMapper.map(pushNotificationRequestMessage);

    fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);
  }
}
