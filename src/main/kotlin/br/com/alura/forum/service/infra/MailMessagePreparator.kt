package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import org.springframework.mail.javamail.MimeMessageHelper

fun prepare(answer: Answer, messageHelper: MimeMessageHelper, newReplyMailFactory: NewReplyMailFactory) {
    val answeredTopic = answer.topic
    messageHelper.setTo(answeredTopic.owner.email)
    messageHelper.setSubject("Novo comentário em: ${answeredTopic.shortDescription}")
    val messageContent = newReplyMailFactory.generateNewReplyMailContent(answer)
    messageHelper.setText(messageContent, true)
}