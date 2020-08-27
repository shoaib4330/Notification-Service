package com.vend.gateway.notification.autoconfig.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vend.notification.mail")
@Getter
@Setter
public class SmtpEmailConfigurationProperties implements InitializingBean {

    /**
     * Enable or disable emailing feature
     */
    private Boolean enabled;

    /**
     * Host of the SMTP server
     */
    private String host;

    /**
     * Port of the SMTP server
     */
    private Integer port;

    /**
     * Enable or disable ssl
     */
    private Boolean sslEnabled;

    /**
     * Configures timeout for SMTP connection
     */
    private String connectionTimeout;

    private String smtpTimeout;

    private Boolean tlsEnabled;

    /**
     * Username/Email to send emails from
     */
    private String username;

    /**
     * Password for the provided username/email
     */
    private String password;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
