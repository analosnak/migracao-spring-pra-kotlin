package br.com.alura.forum.validator

import br.com.alura.forum.controller.dto.input.NewTopicInputDto
import br.com.alura.forum.model.PossibleSpammer
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.TopicRepository
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.time.Instant
import java.time.temporal.ChronoUnit

class NewTopicCustomValidator(private val topicRepository: TopicRepository,
                              private val loggedUser: User) : Validator {

    override fun supports(clazz: Class<*>) = NewTopicInputDto::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {

        val oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS)
        val topics = this.topicRepository
                .findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(loggedUser, oneHourAgo)

        val possibleSpammer = PossibleSpammer(topics)

        if (possibleSpammer.hasTopicLimitExceeded()) {
            val minutesToNextTopic = possibleSpammer.minutesToNextTopic(oneHourAgo)

            errors.reject("newTopicInputDto.limit.exceeded",
                    arrayOf(minutesToNextTopic),
                    "O limite individual de novos tópicos por hora foi excedido")
        }
    }
}