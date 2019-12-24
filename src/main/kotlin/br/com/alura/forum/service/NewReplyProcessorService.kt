package br.com.alura.forum.service

import br.com.alura.forum.model.Answer
import br.com.alura.forum.repository.AnswerRepository
import br.com.alura.forum.service.infra.ForumMailService
import org.springframework.stereotype.Service

@Service
class NewReplyProcessorService(private val answerRepository: AnswerRepository,
                               private val forumMailService: ForumMailService) {

    fun execute(answer: Answer) {
        this.answerRepository.save(answer)
        this.forumMailService.sendNewReplyMailAsync(answer)
    }
}