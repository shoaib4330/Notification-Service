package com.vend.gateway.notification.messaging.fcm.rest.request;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.error.type.impl.NotificationErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.Arrays;

public class FcmAccessTokenProvider {

  private final Logger log = LoggerFactory.getLogger(FcmAccessTokenProvider.class);

  private String fcmAccessTokenRequestScope;

  private String fcmPrivateKeyFileName;

  public FcmAccessTokenProvider(String fcmAccessTokenRequestScope, String fcmPrivateKeyFileName) {
    this.fcmAccessTokenRequestScope = fcmAccessTokenRequestScope;
    this.fcmPrivateKeyFileName = fcmPrivateKeyFileName;
  }

  @Cacheable(cacheNames = "fcm-access-token")
  public String getAccessToken() {
    log.info("FCM access token has expired in cache so requesting new access token from Google");

    GoogleCredential googleCredential;

    try {
      googleCredential =
          GoogleCredential.fromStream(
                  getClass().getClassLoader().getResourceAsStream(fcmPrivateKeyFileName + ".json"))
              .createScoped(Arrays.asList(fcmAccessTokenRequestScope));
      googleCredential.refreshToken();
      return googleCredential.getAccessToken();
    } catch (IOException ex) {
      throw new VendException(NotificationErrorType.UNABLE_TO_READ_FCM_KEY);
    }
  }
}
