package com.vend.gateway.notification.messaging.fcm.rest.response.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error implements Serializable {
    private static final long serialVersionUID = -1L;
    private int code;
    private String message;
    private String status;
}
