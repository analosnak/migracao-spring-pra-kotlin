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

        val messagePreparator = MimeMessagePreparator {
            prepare(answer, MimeMessageHelper(it), newReplyMailFactory)
        }

        try {
            mailSender.send(messagePreparator)
        } catch (e: MailException) {
            logger.error("Não foi possível enviar email para ${answer.topic.owner.email}", e.message)
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(ForumMailService::class.java)
    }
}