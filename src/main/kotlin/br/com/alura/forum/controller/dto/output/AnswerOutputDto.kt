package br.com.alura.forum.controller.dto.output

import br.com.alura.forum.model.Answer
import java.time.Instant

class AnswerOutputDto(answer: Answer) {
    val id: Long = answer.id
    val content: String = answer.content
    val creationTime: Instant = answer.creationTime
    val solution: Boolean = answer.isSolution
    val ownerName: String = answer.ownerName

    companion object {
        fun listFromAnswers(answers: List<Answer>) = answers
                .map(::AnswerOutputDto)
    }
}