package br.com.alura.forum.repository

import br.com.alura.forum.model.Answer
import org.springframework.data.repository.Repository

interface AnswerRepository : Repository<Answer, Long> {

    fun save(answer: Answer): Answer
}