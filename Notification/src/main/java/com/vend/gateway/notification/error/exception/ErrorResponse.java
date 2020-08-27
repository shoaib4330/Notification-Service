package com.vend.gateway.notification.error.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String errorCode;
  private String message;
  private String operationMessage;
  private Map<String, String> fields;
  private HttpStatus httpStatus;
}
