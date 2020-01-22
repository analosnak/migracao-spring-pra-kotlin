package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class ForumMailService(private val mailSender: JavaMailSender,
                       private val newReplyMailFactory: NewReplyMailFactory) {

    @Async
    fun sendNewReplyMailAsync(answer: Answer) {
        val answeredTopic = answer.topic

        val messagePreparator = MimeMessagePreparator { mimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage)
            messageHelper.setTo(answeredTopic.owner.email)
            messageHelper.setSubject("Novo comentário em: ${answeredTopic.shortDescription}")
            val messageContent = this.newReplyMailFactory.generateNewReplyMailContent(answer)
            messageHelper.setText(messageContent, true)
        }

        try {
            mailSender.send(messagePreparator)
        } catch (e: MailException) {
            logger.error("Não foi possível enviar email para ${answeredTopic.owner.email}", e.message)
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(ForumMailService::class.java)
    }
}