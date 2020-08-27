package com.vend.gateway.notification.messaging.mail.impl;

import com.vend.gateway.notification.error.EmailErrorType;
import com.vend.gateway.notification.error.exception.RemoteVendException;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.mail.EmailService;
import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import com.vend.gateway.notification.messaging.mail.client.MailClient;
import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  private MailClient mailClient;
  private TemplateDataMapper templateDataMapper;

  @Autowired
  public EmailServiceImpl(MailClient mailClient, TemplateDataMapper templateDataMapper) {
    this.mailClient = mailClient;
    this.templateDataMapper = templateDataMapper;
  }

  @Override
  @Retryable(
      value = {RemoteVendException.class},
      maxAttemptsExpression = "${vend.notification.max-retries}",
      backoff = @Backoff(delayExpression = "${vend.notification.retry-delay}"))
  public void sendEmail(MailMessageRequest message) {
    validateMailRequest(message);
    log.info("Sending Email to {}", message.getTo());
    sendEmail(message, null, null);
  }

  private void sendEmail(MailMessageRequest mailMessageRequest, String logoPath, String logoCID) {
    /* Email content populated*/
    String mailWithPopulatedContent =
        templateDataMapper.mapDataToPlaceHolders(
            mailMessageRequest.getEmailTemplateWithPlaceHolders(),
            mailMessageRequest.getEmailDataValues());

    /* MailClient to send the prepared email to all the recipients */
    mailClient.email(
        mailMessageRequest,
        mailMessageRequest.getSubject(),
        mailWithPopulatedContent,
        logoPath,
        logoCID);
  }

  private void validateMailRequest(MailMessageRequest message) {
    if (message == null) throw new VendException(EmailErrorType.MAIL_REQUEST_NULL);

    if (CollectionUtils.isEmpty(message.getTo()))
      throw new VendException(EmailErrorType.NO_RECIPIENT_SPECIFIED);

    if (StringUtils.isEmpty(message.getFrom()))
      throw new VendException(EmailErrorType.MAIL_FROM_ATTRIBUTE_INVALID);

    EmailValidator emailValidator = EmailValidator.getInstance();
    if (Boolean.FALSE == emailValidator.isValid(message.getFrom())
        || (message.getTo().stream()
            .anyMatch(email -> emailValidator.isValid(email.getEmail()) == Boolean.FALSE))) {
      throw new VendException(EmailErrorType.EMAIL_INVALID);
    }
  }
}
