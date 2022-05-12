package at.deckweiss.email

import at.deckweiss.email.model.EmailAttachment
import jakarta.mail.internet.InternetAddress

interface EmailClient {
  fun send(receiver: List<InternetAddress>, subject: String, content: String, fromEmail: InternetAddress, cc: List<InternetAddress>? = null, bcc: List<InternetAddress>? = null, attachments: List<EmailAttachment>? = null)
  fun sendAsHtml(receiver: List<InternetAddress>, subject: String, html: String, fromEmail: InternetAddress, cc: List<InternetAddress>? = null, bcc: List<InternetAddress>? = null, attachments: List<EmailAttachment>? = null)
}