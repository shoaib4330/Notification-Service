package com.vend.gateway.notification.messaging.sms.impl.wrapper;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vend.gateway.notification.autoconfig.sms.TwilioSMSConfigurationProperties;
import com.vend.gateway.notification.error.SMSErrorType;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.sms.dto.SMSMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
public class TwilioWrapper {

  private TwilioSMSConfigurationProperties twilioProperties;

  @Autowired
  public TwilioWrapper(TwilioSMSConfigurationProperties twilioProperties) {
    this.twilioProperties = twilioProperties;
  }

  @PostConstruct
  public void init() {
    Twilio.init(twilioProperties.getAccountSID(), twilioProperties.getTwilioAuthToken());
  }

  public Message.Status sendSMS(String toPhoneNumber, String messageText) {
    if (StringUtils.isEmpty(toPhoneNumber))
      throw new VendException(SMSErrorType.NO_RECIPIENT_SPECIFIED);
    if (StringUtils.isEmpty(messageText))
      throw new VendException(SMSErrorType.SMS_TEMPLATE_INVALID);

    Message message =
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioProperties.getTwilioContactNumber()),
                messageText)
            .create();
    log.info("Sent SMS, response received : {}", message);

    return message.getStatus();
  }
}
