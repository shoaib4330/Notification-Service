# README #
This module implements <b>messaging, notification</b> features, and it is a <b>spring-boot</b> based project.
Developers can easily include this module to use the provided features by while setting minimal configuration.
Following are the features that you can easily configure and get up and running conveniently:

- Sending Emails
- Sending SMS
- Sending Firebase Push Notifications 
- Retry Mechanism (upon failure of sent Messages/Notifications it tries resending them on its own)

* #####Version: 0.0.1-SNAPSHOT
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How to set up? ###
* Configuration
  * Spring Autoconfiguration is put in place to help you provide minimum configuration to
  get any feature up and running.
  
  #### Configure Retry Mechanism:
  To enable Retry functionaly:
    - Set `vend.notification.max-retries` to > 0
    - Set `vend.notification.retry-delay` (example: 5000, that is, 5 seconds BackOff)
    - To Disable the retry mechanism, Set `vend.notification.max-retries` to 0
  
  #### Get Emails up and running:
  Following are the configuration paramters for setting up emails and are self explanatory.
  To enable Emails:
  - Set `vend.notification.mail` to `true`
  - Provide values for Mandatory fields
  - Spring AutoConfig will provision all required depedencies. And as a developer just:
    * ```
      @Autowire
      private EmailService emailService;
      ```
    * ```
      someFunction(){
        emailService.sendEmail(Params...);
      }
      ```
  Mandatory attributes:
  ```
  vend:
    notification:
      mail:
        enabled: true/false        (*- Mandatory Attribute -> enables/disables email feature)
        host: ${mail.host}         (*- Mandatory Attribute)
        port: ${mail.port}
        ssl-enabled: false
        tls-enabled: true
        username: ${mail.username} (*- Mandatory Attribute)
        password: ${mail.password} (*- Mandatory Attribute)
        connection-timeout: 2000
        smtp-timeout: 5000
  ```
* Dependencies
  * ```
    <dependency>
                <groupId>com.github.nithril</groupId>
                <artifactId>smtp-connection-pool</artifactId>
                <version>1.4.0</version>
    </dependency>
    
     <dependency>
                <groupId>com.google.api-client</groupId>
                <artifactId>google-api-client</artifactId>
                <version>1.25.0</version>
     </dependency>
    ```
  #### Get SMS up and running:
  Following are the configuration paramters for setting up SMS and are self explanatory.
    To enable SMS:
    - Set `vend.notification.twilio.sms` to `true`
    - Provide values for Mandatory fields
    - Spring AutoConfig will provision all required depedencies. And as a developer just:
      * ```
        @Autowire
        private SMSService smsService;;
        ```
      * ```
        someFunction(){
          smsService.sendSMS(Params...);
        }
        ```
    Mandatory attributes:
    ```
    vend:
      notification:
        sms:
          twilio:
            enabled:               true/false                  (*- Mandatory Attribute -> enables/disables SMS feature)
            account-s-i-d:         ${twilio.account.sid}       (*- Mandatory Attribute)
            twilio-auth-token:     ${twilio.auth.token}        (*- Mandatory Attribute)
            twilio-contact-number: ${twilio.contact-number}    (*- Mandatory Attribute)
    ```
  * Dependencies
      ```
        <dependency>
            <groupId>com.twilio.sdk</groupId>
            <artifactId>twilio</artifactId>
            <version>7.49.1</version>
        </dependency>
      ```
  #### Get FCM Notifications up and running:
  Following are the configuration paramters for setting up notifications and are self explanatory.
  To enable FCM Notifications:
  - Set `vend.notification.push.enable` to `true`
  - Provide values for Mandatory fields
  - Spring AutoConfig will provision all required depedencies. And as a developer just:
    * ```
      @Autowire
      private FcmPushNotificationService fcmPushNotificationService;
      ```
    * ```
      someFunction(){
        fcmPushNotificationService.sendPushNotification(Params...);
      }
      ```
  Mandatory attributes:
  ```
  vend:
    notification:
          push:
            fcm:
              enabled: true
              google-fcm-url: https://fcm.googleapis.com/
              google-fcm-access-token-request-scope: https://www.googleapis.com/auth/firebase.messaging
              google-fcm-private-key-file: fcm-private-default-key
              google-fcm-connect-timeout-milliseconds: 2000
              google-fcm-read-timeout-milliseconds: 5000
  ```
* Dependencies
  * ```
        <dependency>
            <groupId>com.google.api-client</groupId>
            <artifactId>google-api-client</artifactId>
            <version>1.25.0</version>
        </dependency>
    ```
* Database configuration
  * No database configuration required.
* How to run tests
  * mvn clean install (this will run tests also)
* Deployment instructions
  * This module will be included as a Maven dependency within your project.

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact