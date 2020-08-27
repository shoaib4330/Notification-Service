package com.vend.gateway.notification.messaging.fcm.rest.request;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class AccessTokenHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    private FcmAccessTokenProvider fcmAccessTokenProvider;

    public AccessTokenHeaderRequestInterceptor(FcmAccessTokenProvider fcmAccessTokenProvider){
            this.fcmAccessTokenProvider = fcmAccessTokenProvider;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + fcmAccessTokenProvider.getAccessToken());
        return execution.execute(request, body);
    }
}
