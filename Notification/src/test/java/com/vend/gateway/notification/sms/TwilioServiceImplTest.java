package com.vend.gateway.notification.sms;

import com.google.common.collect.ImmutableMap;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;
import com.vend.gateway.notification.messaging.mail.dto.To;
import com.vend.gateway.notification.messaging.sms.dto.SMSMessageRequest;
import com.vend.gateway.notification.messaging.sms.impl.TwilioSMSServiceImpl;
import com.vend.gateway.notification.messaging.sms.impl.wrapper.TwilioWrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TwilioServiceImplTest {

  private static final String SMS_TO = "+923348782816";
  private static final String SMS_NAME = "Shaloom";
  private static final String SMS_TEMPLATE =
      "Hey Mr. ${NAME}, stay updated about COVID-19 latest news.";
  private static final String SMS_FORMED_MESSAGE =
          "Hey Mr. Shaloom, stay updated about COVID-19 latest news.";

  @InjectMocks TwilioSMSServiceImpl smsService;
  @Mock private TwilioWrapper twilioWrapper;
  @Spy private TemplateDataMapper templateDataMapper;

  @Test
  public void testSendSMSWhenSuccess() {
    SMSMessageRequest smsMessageRequest = SMSMessageRequest.builder()
            .toPhoneNumber(SMS_TO)
            .smsTemplateWithPlaceHolders(SMS_TEMPLATE)
            .smsDataValues(ImmutableMap.<String, String>of("NAME", SMS_NAME))
            .build();

    smsService.sendSMS(smsMessageRequest);

    Mockito.verify(twilioWrapper, Mockito.times(1))
            .sendSMS(SMS_TO, SMS_FORMED_MESSAGE);

    Mockito.verify(templateDataMapper, Mockito.times(1))
        .mapDataToPlaceHolders(Mockito.anyString(), Mockito.anyMap());
  }

  @Test(expected = VendException.class)
  public void testSendSMSWhenToIsEmpty() {
    SMSMessageRequest smsMessageRequest = SMSMessageRequest.builder()
            .toPhoneNumber("")
            .smsTemplateWithPlaceHolders(SMS_TEMPLATE)
            .smsDataValues(ImmutableMap.<String, String>of("NAME", SMS_NAME))
            .build();

    smsService.sendSMS(smsMessageRequest);
  }

  @Test(expected = VendException.class)
  public void testSendEmailWhenMessageTextIsEmpty() {
    SMSMessageRequest smsMessageRequest = SMSMessageRequest.builder()
            .toPhoneNumber(SMS_TO)
            .smsTemplateWithPlaceHolders("")
            .smsDataValues(ImmutableMap.<String, String>of("NAME", SMS_NAME))
            .build();

    smsService.sendSMS(smsMessageRequest);
  }
}
