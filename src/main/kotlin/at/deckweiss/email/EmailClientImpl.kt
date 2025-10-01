package at.deckweiss.email

import at.deckweiss.email.model.EmailAttachment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import jakarta.mail.internet.InternetAddress

class EmailClientImpl(private val javaMailSender: JavaMailSender, private val environment: Environment) : EmailClient {
  private val logger: Logger = LoggerFactory.getLogger(EmailClientImpl::class.java)

  override fun send(receiver: List<InternetAddress>, subject: String, content: String, fromEmail: InternetAddress, cc: List<InternetAddress>?, bcc: List<InternetAddress>?, attachments: List<EmailAttachment>?) {
    sendEmail(receiver, subject, content, fromEmail, cc, bcc, attachments)
  }

  override fun sendAsHtml(receiver: List<InternetAddress>, subject: String, html: String, fromEmail: InternetAddress, cc: List<InternetAddress>?, bcc: List<InternetAddress>?, attachments: List<EmailAttachment>?) {
    sendEmail(receiver, subject, html, fromEmail, cc, bcc, attachments, true)
  }

  private fun sendEmail(receiver: List<InternetAddress>, subject: String, content: String, fromEmail: InternetAddress, cc: List<InternetAddress>?, bcc: List<InternetAddress>?, attachments: List<EmailAttachment>?, isHtmlContent: Boolean = false) {
    if (environment.getProperty("spring.mail.enabled")?.toBooleanStrictOrNull() == false) {
      logger.debug("E-Mail service is disabled. Skipping email (receiver: {})", receiver.joinToString(",") { it.address })
      return
    }

    try {
      val mimeMessage = javaMailSender.createMimeMessage()
      val helper = MimeMessageHelper(mimeMessage, !attachments.isNullOrEmpty(), "UTF-8")

      helper.setText(content, isHtmlContent)
      helper.setTo(receiver.toTypedArray())
      helper.setSubject(subject)
      helper.setFrom(fromEmail)
      cc?.let { helper.setCc(it.toTypedArray()) }
      bcc?.let { helper.setBcc(it.toTypedArray()) }
      attachments?.let { a -> a.forEach { emailAttachment -> helper.addAttachment(emailAttachment.name, ByteArrayResource(emailAttachment.content)) } }

      javaMailSender.send(mimeMessage)
    } catch (e: Exception) {
      throw EmailClientException(e.message ?: "Error sending email", e)
    }
  }
}