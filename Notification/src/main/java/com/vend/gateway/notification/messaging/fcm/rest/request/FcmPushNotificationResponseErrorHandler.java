package com.vend.gateway.notification.messaging.fcm.rest.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.vend.gateway.notification.error.exception.RemoteVendException;
import com.vend.gateway.notification.messaging.fcm.rest.response.error.FcmPushNotificationErrorResponseMessage;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.error.type.impl.NotificationErrorType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class FcmPushNotificationResponseErrorHandler implements ResponseErrorHandler {

    private ObjectReader jsonReader =
            new ObjectMapper().readerFor(FcmPushNotificationErrorResponseMessage.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        FcmPushNotificationErrorResponseMessage message;
        try {
            if(!response.getStatusCode().is2xxSuccessful()){
                throw new RemoteVendException("Error in http call", "Remote call to FCM has error", response.getStatusCode());
            }
            message = jsonReader.readValue(response.getBody());
            log.info("response:" + message.toString());

        } catch (Exception ex) {
            log.error("Exception occured:", ex);
            throw new VendException(NotificationErrorType.FCM_ERROR_RESPONSE);
        }
        log.error("Error occured while sending push notification");
        throw new VendException(NotificationErrorType.FCM_ERROR_RESPONSE);
    }
}
