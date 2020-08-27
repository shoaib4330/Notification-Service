package com.vend.gateway.notification.messaging.sms.impl;

import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.vend.gateway.notification.error.SMSErrorType;
import com.vend.gateway.notification.error.exception.RemoteVendException;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.mail.TemplateDataMapper;
import com.vend.gateway.notification.messaging.sms.SMSService;
import com.vend.gateway.notification.messaging.sms.dto.SMSMessageRequest;
import com.vend.gateway.notification.messaging.sms.impl.wrapper.TwilioWrapper;
import com.vend.gateway.notification.messaging.sms.util.PhoneValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Slf4j
public class TwilioSMSServiceImpl implements SMSService<Message.Status> {

    private TwilioWrapper smsSender;

    private TemplateDataMapper templateDataMapper;

    @Autowired
    public TwilioSMSServiceImpl(TwilioWrapper smsSender, TemplateDataMapper templateDataMapper) {
        this.smsSender = smsSender;
        this.templateDataMapper = templateDataMapper;
    }

    @Override
    @Retryable(value = {ApiException.class}, maxAttemptsExpression = "${vend.notification.max-retries}", backoff = @Backoff(delayExpression = "${vend.notification.retry-delay}"))
    public Message.Status sendSMS(SMSMessageRequest smsMessageRequest) {
        log.info("sendSMS() with payload : {}", smsMessageRequest);
        validateSMSRequest(smsMessageRequest);
        return smsSender.sendSMS(smsMessageRequest.getToPhoneNumber(), prepareSMSText(smsMessageRequest));
    }

    private String prepareSMSText(SMSMessageRequest smsMessageRequest) {
        return templateDataMapper.mapDataToPlaceHolders(
                smsMessageRequest.getSmsTemplateWithPlaceHolders(),
                smsMessageRequest.getSmsDataValues());
    }

    private void validateSMSRequest(SMSMessageRequest smsMessageRequest){
        if(smsMessageRequest == null)
            throw new VendException(SMSErrorType.SMS_REQUEST_NULL);
        if(StringUtils.isEmpty(smsMessageRequest.getToPhoneNumber()))
            throw new VendException(SMSErrorType.NO_RECIPIENT_SPECIFIED);
        if(StringUtils.isEmpty(smsMessageRequest.getSmsTemplateWithPlaceHolders()))
            throw new VendException(SMSErrorType.SMS_TEMPLATE_INVALID);
        if(PhoneValidationUtil.isValidPhoneNumber(smsMessageRequest.getToPhoneNumber())==Boolean.FALSE)
            throw new VendException(SMSErrorType.INVALID_PHONE_NUMBER);
    }


}
