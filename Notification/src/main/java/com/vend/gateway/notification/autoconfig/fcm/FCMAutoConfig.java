package com.vend.gateway.notification.autoconfig.fcm;

import com.vend.gateway.notification.autoconfig.mail.SmtpEmailConfigurationProperties;
import com.vend.gateway.notification.messaging.fcm.rest.request.AccessTokenHeaderRequestInterceptor;
import com.vend.gateway.notification.messaging.fcm.rest.request.FcmAccessTokenProvider;
import com.vend.gateway.notification.messaging.fcm.rest.request.FcmPushNotificationResponseErrorHandler;
import com.vend.gateway.notification.messaging.fcm.rest.request.LoggingRequestInterceptor;
import com.vend.gateway.notification.messaging.fcm.service.FcmPushNotificationService;
import com.vend.gateway.notification.messaging.fcm.service.impl.FcmPushNotificationServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(FCMConfigurationProperties.class)
public class FCMAutoConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.push.fcm",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    @ConditionalOnMissingBean
    public FcmPushNotificationService fcmPushNotificationService(
            FCMConfigurationProperties fcmConfigurationProperties) {
        return new FcmPushNotificationServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public RestTemplate fcmRestTemplate(AccessTokenHeaderRequestInterceptor accessTokenHeaderRequestInterceptor, LoggingRequestInterceptor loggingRequestInterceptor,
            FCMConfigurationProperties configurationProperties) {
        return new RestTemplateBuilder()
                .requestFactory(
                        () ->
                                new BufferingClientHttpRequestFactory(
                                        new OkHttp3ClientHttpRequestFactory()))
                .additionalInterceptors(accessTokenHeaderRequestInterceptor, loggingRequestInterceptor)
                .errorHandler(new FcmPushNotificationResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(configurationProperties.getGoogleFcmConnectTimeoutMilliseconds()))
                .setReadTimeout(Duration.ofMillis(configurationProperties.getGoogleFcmReadTimeoutMilliseconds()))
                .build();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.push.fcm",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    public AccessTokenHeaderRequestInterceptor accessTokenHeaderRequestInterceptor(
            FcmAccessTokenProvider fcmAccessTokenProvider) {
        return new AccessTokenHeaderRequestInterceptor(fcmAccessTokenProvider);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.push.fcm",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    public LoggingRequestInterceptor loggingRequestInterceptor() {
        return new LoggingRequestInterceptor();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.push.fcm",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    public FcmAccessTokenProvider fcmAccessTokenProvider(
            FCMConfigurationProperties fcmConfigurationProperties) {

        return new FcmAccessTokenProvider(
                fcmConfigurationProperties.getGoogleFcmAccessTokenRequestScope(),
                fcmConfigurationProperties.getGoogleFcmPrivateKeyFile());
    }
}
