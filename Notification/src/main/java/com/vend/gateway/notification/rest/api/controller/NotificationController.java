package com.vend.gateway.notification.rest.api.controller;

import com.vend.gateway.notification.messaging.fcm.dto.PushNotificationRequestMessage;
import com.vend.gateway.notification.messaging.fcm.service.FcmPushNotificationService;
import com.vend.gateway.notification.messaging.mail.EmailService;
import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;
import com.vend.gateway.notification.messaging.mail.dto.To;
import com.vend.gateway.notification.messaging.sms.SMSService;
import com.vend.gateway.notification.messaging.sms.dto.SMSMessageRequest;
import com.vend.gateway.notification.rest.api.dto.BooleanResponse;
import com.vend.gateway.notification.rest.api.dto.EmailRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/notification/api/v1")
public class NotificationController {

  @Autowired private SMSService smsService;

  @Autowired private EmailService emailService;

  @Autowired private FcmPushNotificationService fcmPushNotificationService;

  @PostMapping(value = "notifications/sms/send")
  public BooleanResponse smsSend(@RequestBody SMSMessageRequest smsRequestDTO) {
    smsService.sendSMS(smsRequestDTO);
    return BooleanResponse.success("SMS sent successfully");
  }

  @PostMapping("notifications/email/send")
  public BooleanResponse emailSend(@RequestBody EmailRequestDto emailRequestDto) {
    MailMessageRequest mailMessageRequest =
        MailMessageRequest.builder()
            .from(emailRequestDto.getFrom())
            .to(Collections.<To>singletonList(To.builder().email(emailRequestDto.getTo()).build()))
            .subject(emailRequestDto.getSubject())
            .emailTemplateWithPlaceHolders(emailRequestDto.getEmailTemplateWithPlaceHolders())
            .emailDataValues(emailRequestDto.getEmailDataValues())
            .build();
    emailService.sendEmail(mailMessageRequest);
    return BooleanResponse.success("Email sent successfully");
  }

  @PostMapping("notifications/fcm/send")
  public BooleanResponse notificationSend(
      @RequestBody PushNotificationRequestMessage pushNotificationRequestMessage) {
    fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);
    return BooleanResponse.success("FCM Notification sent successfully");
  }

  //    @GetMapping("notifications/sms/send")
  //    public BooleanResponse sendSMS() {
  //        smsService.sendSMS(
  //                SMSMessageRequest.builder()
  //                        .toPhoneNumber("+923349521400")
  //                        .smsTemplateWithPlaceHolders("This is a TEST message from Twilio")
  //                        .build());
  //        return BooleanResponse.success("SMS sent successfully");
  //    }
  //
  //    @GetMapping("notifications/email/send")
  //    public BooleanResponse sendEmail() {
  //        MailMessageRequest mailMessageRequest =
  //                MailMessageRequest.builder()
  //                        .from("shoaib.anwar@venturedive.com")
  //
  // .to(Collections.<To>singletonList(To.builder().email("shoaib.anwar@hotmail.com").build()))
  //                        .subject("Hi There")
  //                        .emailTemplateWithPlaceHolders("<Hello <b>${MR}</b>, <br> This is a test
  // <b>Email</b>")
  //                        .emailDataValues(
  //                                MapUtils.putAll(
  //                                        new HashMap<String, String>(),
  //                                        new Map.Entry[] {new DefaultMapEntry("MR", "Shoaib
  // Anwar")}))
  //                        .build();
  //
  //        emailService.sendEmail(mailMessageRequest);
  //
  //        return BooleanResponse.success("Email sent successfully");
  //    }
  //
  //    @GetMapping("notifications/fcm/send")
  //    public BooleanResponse sendFCMNotification() {
  //        PushNotificationRequestMessage pushNotificationRequestMessage =
  //                PushNotificationRequestMessage.builder()
  //                        .fcmRegistrationId("sier83n7275t258rcvr")
  //                        .titleTemplateWithPlaceHolders("Hey Mr. ${NAME}")
  //                        .bodyTemplateWithPlaceHolders("Fresh good news for you related to
  // ${RELATED_THING}")
  //                        .placeHolderDataForTitle(ImmutableMap.<String, String>of("NAME", "Aziz
  // Khan"))
  //                        .placeHolderDataForBody(
  //                                ImmutableMap.<String, String>of("RELATED_THING", "CORONA,
  // COVID-19"))
  //                        .build();
  //
  //        fcmPushNotificationService.sendPushNotification(pushNotificationRequestMessage);
  //
  //        return BooleanResponse.success("FCM Notification sent successfully");
  //    }
}
