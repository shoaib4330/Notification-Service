package com.vend.gateway.notification.messaging.sms.util;

import org.apache.commons.lang3.StringUtils;

public class PhoneValidationUtil {
  private static String pattern =
      "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";

  public static boolean isValidPhoneNumber(String phoneNumber) {
    if (StringUtils.isNotEmpty(phoneNumber)) {
      return phoneNumber.matches(pattern);
    }
    return false;
  }
}
