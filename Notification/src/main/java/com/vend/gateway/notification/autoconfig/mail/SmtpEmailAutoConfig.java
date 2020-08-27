package com.vend.gateway.notification.autoconfig.mail;

import com.vend.gateway.notification.messaging.mail.client.MailClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.nlab.smtp.pool.SmtpConnectionPool;
import org.nlab.smtp.transport.factory.SmtpConnectionFactories;
import org.nlab.smtp.transport.factory.SmtpConnectionFactory;
import org.nlab.smtp.transport.factory.SmtpConnectionFactoryBuilder;
import org.nlab.smtp.transport.strategy.TransportStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(SmtpEmailConfigurationProperties.class)
public class SmtpEmailAutoConfig {

  @Bean
  @ConditionalOnProperty(
      prefix = "vend.notification.mail",
      name = "enabled",
      havingValue = "true",
      matchIfMissing = false)
  @ConditionalOnMissingBean
  public MailClient mailClient(SmtpEmailConfigurationProperties smtpEmailConfigurationProperties) {
    return new MailClient();
  }

  @Bean
  @ConditionalOnMissingBean
  public SmtpConnectionPool smtpConnectionPool(SmtpConnectionFactory smtpConnectionFactory) {
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    config.setMaxTotal(8);
    return new SmtpConnectionPool(smtpConnectionFactory, config);
  }

  @Bean
  @ConditionalOnMissingBean
  public SmtpConnectionFactory smtpConnectionFactory(Session session, SmtpEmailConfigurationProperties configurationProperties) {
    return SmtpConnectionFactoryBuilder.newSmtpBuilder()
            .session(session)
            .protocol("smtp")
            .host(configurationProperties.getHost())
            .port(configurationProperties.getPort())
            .username(configurationProperties.getUsername())
            .password(configurationProperties.getPassword())
            .build();
  }

  private Properties properties(SmtpEmailConfigurationProperties smtpProperties) {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", String.valueOf(smtpProperties.getTlsEnabled()));
    properties.put("mail.smtp.host", smtpProperties.getHost());
    properties.put("mail.smtp.port", String.valueOf(smtpProperties.getPort()));
    // todo: allow the below attribute to be configured as a property as well
    properties.put("mail.debug", "true");
    properties.put("mail.smtp.ssl.trust", smtpProperties.getHost());
    properties.put("mail.smtp.connectiontimeout", smtpProperties.getConnectionTimeout());
    properties.put("mail.smtp.timeout", smtpProperties.getSmtpTimeout());
    return properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public Session session(SmtpEmailConfigurationProperties smtpEmailConfigurationProperties) {
    Properties properties = properties(smtpEmailConfigurationProperties);
    return Session.getDefaultInstance(
            properties,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        smtpEmailConfigurationProperties.getUsername(),
                        smtpEmailConfigurationProperties.getPassword());
              }
            });
  }
}
