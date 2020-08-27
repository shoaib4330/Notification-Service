package com.vend.gateway.notification.autoconfig.fcm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vend.notification.push.fcm")
@Getter
@Setter
public class FCMConfigurationProperties implements InitializingBean {

    /**
     * Enable or disable FCM Push Notifications Feature
     */
    private Boolean enabled;
    /**
     * Endpoint for FCM messaging
     */
    private String googleFcmUrl;
    /**
     * AccessToken Request Scope to used with AccessToken
     */
    private String googleFcmAccessTokenRequestScope;
    /**
     * Path to the FCM PrivateKeyFile
     */
    private String googleFcmPrivateKeyFile;
    /**
     * Connect Timeout
     */
    private Integer googleFcmConnectTimeoutMilliseconds;
    /**
     * Read Timeout
     */
    private Integer googleFcmReadTimeoutMilliseconds;

    @Override
    public void afterPropertiesSet() throws Exception {}
}
