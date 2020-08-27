package com.vend.gateway.notification.messaging.mail;

import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;

public interface EmailService {
    /**
     * Sends email as per the details/data in {@link MailMessageRequest} message parameter
     * @param message
     */
    void sendEmail(final MailMessageRequest message);
}
