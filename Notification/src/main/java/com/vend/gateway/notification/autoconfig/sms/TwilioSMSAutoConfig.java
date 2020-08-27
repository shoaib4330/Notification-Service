package com.vend.gateway.notification.autoconfig.sms;

import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import com.vend.gateway.notification.messaging.sms.SMSService;
import com.vend.gateway.notification.messaging.sms.impl.TwilioSMSServiceImpl;
import com.vend.gateway.notification.messaging.sms.impl.wrapper.TwilioWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TwilioSMSConfigurationProperties.class)
public class TwilioSMSAutoConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.sms.twilio",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    @ConditionalOnMissingBean
    public SMSService twilioSMSService(TwilioWrapper twilioWrapper, TemplateDataMapper templateDataMapper) {
        return new TwilioSMSServiceImpl(twilioWrapper, templateDataMapper);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "vend.notification.sms.twilio",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = false)
    @ConditionalOnMissingBean
    public TwilioWrapper twilioWrapper(TwilioSMSConfigurationProperties twilioSMSConfigurationProperties){
        return new TwilioWrapper(twilioSMSConfigurationProperties);
    }
}
