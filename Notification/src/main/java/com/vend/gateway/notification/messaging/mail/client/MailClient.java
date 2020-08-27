package com.vend.gateway.notification.messaging.mail.client;

import com.vend.gateway.notification.autoconfig.mail.SmtpEmailConfigurationProperties;
import com.vend.gateway.notification.error.EmailErrorType;
import com.vend.gateway.notification.error.exception.VendException;
import com.vend.gateway.notification.messaging.mail.dto.MailMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nlab.smtp.pool.SmtpConnectionPool;
import org.nlab.smtp.transport.connection.ClosableSmtpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MailClient {

  @Autowired private SmtpConnectionPool smtpConnectionPool;

  @Autowired private SmtpEmailConfigurationProperties configurationProperties;

  public void email(
      MailMessageRequest message,
      String emailSubject,
      String mailContentBody,
      String logoPath,
      String logoCID) {

    try (ClosableSmtpConnection closableSmtpConnection = smtpConnectionPool.borrowObject()) {
      MimeMessage email = getMimeMessage(closableSmtpConnection.getSession());

      InternetAddress from = createRecipient(message.getFrom());
      List<InternetAddress> toList =
          message.getTo().stream()
              .map(to -> createRecipient(to.getEmail()))
              .collect(Collectors.toList());

      email.setFrom(from);
      email.setRecipients(Message.RecipientType.TO, toList.toArray(new Address[toList.size()]));
      email.setSubject(emailSubject);
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(mailContentBody, "text/html;charset=UTF-8");

      // add body/text/html
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);

      // add inline logo
      if (StringUtils.isNotEmpty(logoPath) && StringUtils.isNotEmpty(logoCID)) {
        multipart.addBodyPart(createImagePartFromFile(logoPath, logoCID));
      }

      email.setContent(multipart);
      log.debug("Sending email to {}", toList.get(0));
      Transport transport = closableSmtpConnection.getSession().getTransport("smtp");
      if (!transport.isConnected()) {
        transport.connect(configurationProperties.getUsername(), configurationProperties.getPassword());
      }
      transport.sendMessage(email, email.getAllRecipients());
      log.debug("Finished sending email to {}", toList.get(0));
    } catch (MessagingException e) {
      throw new VendException("MailClient Error", "Error sending email", e);
    } catch (Exception e) {
      throw new VendException("MailClient Error", "Error sending email", e);
    }
  }

  private MimeMessage getMimeMessage(Session session) {
    try {
      return new MimeMessage(session);
    }
    catch (Exception e) {
      throw new VendException(
          "SMTP-CONNECTION", "Error while retrieving smtp connection from connection pool", e);
    }
  }
  /**
   * Add inline/embedded images from url
   *
   * @param imgUrl
   * @param cid
   * @return MimeBodyPart (image body part)
   * @throws MessagingException
   */
  private MimeBodyPart createImagePartFromUrl(String imgUrl, String cid) throws MessagingException {
    MimeBodyPart imagePart = new MimeBodyPart();
    imagePart.setDataHandler(createDataHandler(imgUrl));
    imagePart.setHeader("Content-ID", cid);
    imagePart.setDisposition(BodyPart.INLINE);
    return imagePart;
  }

  private DataHandler createDataHandler(String imgUrl) {
    URL url = null;
    try {
      url = new URL(imgUrl);
      URLDataSource ds = new URLDataSource(url);
      return new DataHandler(ds);
    } catch (MalformedURLException e) {
      log.error("Failed to create DataHandler for email", e);
      return null;
    }
  }

  /**
   * Add inline/embedded images from local
   *
   * @param imagePath
   * @param cid
   * @return MimeBodyPart (image body part)
   * @throws MessagingException
   */
  private MimeBodyPart createImagePartFromFile(String imagePath, String cid)
      throws MessagingException {
    MimeBodyPart imagePart = new MimeBodyPart();
    imagePart.setDataHandler(
        new DataHandler(this.getClass().getClassLoader().getResource(imagePath)));
    imagePart.setHeader("Content-ID", cid);
    imagePart.setDisposition(BodyPart.INLINE);
    return imagePart;
  }

  private InternetAddress createRecipient(String email) {
    try {
      return new InternetAddress(email);
    } catch (AddressException e) {
      log.error("Error creating email recipient", e);
      throw new VendException(EmailErrorType.ERROR_CREATING_RECIPIENT);
    }
  }
}
