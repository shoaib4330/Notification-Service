package com.vend.gateway.notification.messaging.mail;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplateDataMapper {

  public String mapDataToPlaceHolders(String placeHolderString, Map<String, String> data) {
    StringSubstitutor stringSubstitutor = new StringSubstitutor(data);
    return stringSubstitutor.setEnableUndefinedVariableException(true).replace(placeHolderString);
  }
}
