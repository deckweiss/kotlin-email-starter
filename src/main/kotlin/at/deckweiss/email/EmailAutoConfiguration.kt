package at.deckweiss.email

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender

@Configuration
open class EmailAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  open fun emailClient(javaMailSender: JavaMailSender, environment: Environment): EmailClient {
    return EmailClientImpl(javaMailSender, environment)
  }
}