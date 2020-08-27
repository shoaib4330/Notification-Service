package com.vend.gateway.notification.mail;

import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import com.vend.gateway.notification.messaging.mail.client.MailClient;
import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;
import com.vend.gateway.notification.messaging.mail.dto.To;
import com.vend.gateway.notification.messaging.mail.impl.EmailServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {
  private static final String EMAIL_FROM = "vend-test-sender@veturedive.com";
  private static final String EMAIL_TO = "vend-test-recipient@veturedive.com";
  private static final String EMAIL_CC = "vend-test-CC@veturedive.com";
  private static final String EMAIL_BCC = "vend-test-BCC@veturedive.com";

  @InjectMocks EmailServiceImpl mailService;
  @Mock private MailClient mailClient;
  @Spy private TemplateDataMapper templateDataMapper;

  @Test(expected = VendException.class)
  public void testSendEmailWhenFromIsEmpty() {
    MailMessageRequest mailMessageRequest =
        MailMessageRequest.builder()
            .from("")
            .to(Collections.<To>singletonList(To.builder().email(EMAIL_TO).build()))
            .cc(To.builder().email(EMAIL_CC).build())
            .bcc(To.builder().email(EMAIL_BCC).build())
            .build();

    mailService.sendEmail(mailMessageRequest);

    Mockito.verify(templateDataMapper, Mockito.times(1))
        .mapDataToPlaceHolders(Mockito.anyString(), Mockito.anyMap());
  }

  @Test(expected = VendException.class)
  public void testSendEmailWhenFromIsNull() {
    MailMessageRequest mailMessageRequest =
        MailMessageRequest.builder()
            .from(null)
            .to(Collections.<To>singletonList(To.builder().email(EMAIL_TO).build()))
            .cc(To.builder().email(EMAIL_CC).build())
            .bcc(To.builder().email(EMAIL_BCC).build())
            .build();

    mailService.sendEmail(mailMessageRequest);

    Mockito.verify(templateDataMapper, Mockito.times(1))
        .mapDataToPlaceHolders(Mockito.anyString(), Mockito.anyMap());
  }

  @Test(expected = VendException.class)
  public void testSendEmailWhenToIsEmpty() {
    MailMessageRequest mailMessageRequest =
        MailMessageRequest.builder()
            .from(EMAIL_FROM)
            .to(Collections.<To>emptyList())
            .cc(To.builder().email(EMAIL_CC).build())
            .bcc(To.builder().email(EMAIL_BCC).build())
            .build();

    mailService.sendEmail(mailMessageRequest);

    Mockito.verify(templateDataMapper, Mockito.times(1))
        .mapDataToPlaceHolders(Mockito.anyString(), Mockito.anyMap());
  }

  @Test
  public void testSendEmailWhenSuccess() {
    MailMessageRequest mailMessageRequest =
        MailMessageRequest.builder()
            .from(EMAIL_FROM)
            .to(Collections.<To>singletonList(To.builder().email(EMAIL_TO).build()))
            .cc(To.builder().email(EMAIL_CC).build())
            .bcc(To.builder().email(EMAIL_BCC).build())
            .emailTemplateWithPlaceHolders("<Hello <b>${MR}</b>, <br> Dieu te bénisse")
            .emailDataValues(
                MapUtils.putAll(
                    new HashMap<String, String>(),
                    new Map.Entry[] {new DefaultMapEntry("MR", "Aziz Khan")}))
            .build();

    mailService.sendEmail(mailMessageRequest);

    Mockito.verify(templateDataMapper, Mockito.times(1))
        .mapDataToPlaceHolders(Mockito.anyString(), Mockito.anyMap());
  }
}
