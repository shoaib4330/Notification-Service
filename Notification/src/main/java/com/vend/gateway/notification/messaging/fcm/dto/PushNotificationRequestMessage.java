package com.vend.gateway.notification.messaging.fcm.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PushNotificationRequestMessage implements Serializable {
    private static final long serialVersionUID = -1L;

    private String fcmRegistrationId;
    private String titleTemplateWithPlaceHolders;
    private String bodyTemplateWithPlaceHolders;
    private Map<String, String> placeHolderDataForBody;
    private Map<String, String> placeHolderDataForTitle;
    private Map<String, String> additionalData;
}
