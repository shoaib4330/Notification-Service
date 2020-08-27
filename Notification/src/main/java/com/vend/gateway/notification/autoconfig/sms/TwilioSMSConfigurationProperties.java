package com.vend.gateway.notification.autoconfig.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "vend.notification.sms.twilio")
@Getter
@Setter
public class TwilioSMSConfigurationProperties implements InitializingBean{
    /**
     * Enable or disable emailing feature
     */
    private Boolean enabled;

    /**
     * Twilio's accountSID
     */
    private String accountSID;

    /**
     * Twilio's Auth_Token
     */
    private String twilioAuthToken;

    /**
     * Contact#/Phone# Twilio provides for your application
     */
    private String twilioContactNumber;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
