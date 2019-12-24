package br.com.alura.forum.infra

import br.com.alura.forum.model.Answer
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class NewReplyMailFactory(private val templateEngine: TemplateEngine) {

    fun generateNewReplyMailContent(answer: Answer): String {
        val answeredTopic = answer.topic

        val thymeleafContext = Context()
        thymeleafContext.setVariable("topicOwnerName", answeredTopic.owner.name)
        thymeleafContext.setVariable("topicShortDescription", answeredTopic.shortDescription)
        thymeleafContext.setVariable("answerAuthor", answer.owner.name)
        thymeleafContext.setVariable("answerCreationInstant", getFormattedCreationTime(answer))
        thymeleafContext.setVariable("answerContent", answer.content)

        return this.templateEngine.process("email-template.html", thymeleafContext)
    }

    private fun getFormattedCreationTime(answer: Answer) =
            DateTimeFormatter.ofPattern("kk:mm")
                    .withZone(ZoneId.of("America/Sao_Paulo"))
                    .format(answer.creationTime)
}