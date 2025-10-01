![Maven Central Version](https://img.shields.io/maven-central/v/at.deckweiss/email-starter?style=flat&label=Maven%20Central)

# Backend E-Mail Starter

This package is based on `spring-boot-starter-email` and enables email transfer over `SMTP`, `IMAP` and `POP3`.

### Installation

Copy the following line to your `build.gradle` dependency list:

```kotlin
implementation("at.deckweiss:email-starter:<version>")
```

### Configuration

All you need to do is to add at least following required properties to your `application.yml`:

```yaml
spring:
  mail:
    # required props (if not provided, there's no JavaMailSender bean available)
    host: "<host>"
    # optional props
    enabled: false # if set to false, emails are not sent; if enabled is true or not set -> emails are sent
    username: "username"
    password: "password"
    port: 25
    properties:
      # JavaMail properties
      "[mail.transport.protocol]": "smtp | imap | pop3"
      "[mail.smtp.connectiontimeout]": 5000
      "[mail.smtp.timeout]": 3000
      "[mail.smtp.writetimeout]": 5000
      "[mail.smtp.auth]": true
      "[mail.smtp.starttls.enable]": true
```

A complete list of `JavaMail` properties can be
found here:

- [SMTP](https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html)
- [IMAP](https://javaee.github.io/javamail/docs/api/com/sun/mail/imap/package-summary.html)
- [POP3](https://javaee.github.io/javamail/docs/api/com/sun/mail/pop3/package-summary.html)

### Usage

A bean of type [EmailClient](src/main/kotlin/at/deckweiss/email/EmailClient.kt) is available to your application (only if you configured the
required props).

```kotlin
class MyEmailService(private val emailClient: EmailClient) {

  fun sendEmail() {
    // send email with text content (text/plain)
    emailClient.send(listOf(InternetAddress("receiver@email.com")), "Subject", "Hello, this is my content.", InternetAddress("no-reply@email.com", "<display name>"))

    // send HTML email
    emailClient.sendAsHtml(listOf(InternetAddress("receiver@email.com")), "Subject", "<html><body>Hello, this is my html content.</body></html>", InternetAddress("no-reply@email.com", "<display name>"))
  }
}
```

### Test services

You might want to test your email transfer using a mock or something similar.  
Thankfully, there are some Saas providers and on-premise solutions:

- [Mailtrap.io](https://mailtrap.io)
- [MailHog](https://github.com/mailhog/MailHog) for docker