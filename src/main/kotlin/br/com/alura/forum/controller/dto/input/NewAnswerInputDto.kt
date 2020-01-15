package br.com.alura.forum.controller.dto.input

import br.com.alura.forum.model.Answer
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class NewAnswerInputDto(@field:NotBlank @field:Size(min = 5) val content: String) {

    fun build(topic: Topic, owner: User) = Answer(this.content, topic, owner)
}