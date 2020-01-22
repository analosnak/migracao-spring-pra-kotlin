package br.com.alura.forum.service

import br.com.alura.forum.model.Answer
import br.com.alura.forum.repository.AnswerRepository
import br.com.alura.forum.service.infra.ForumMailService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NewReplyProcessorServiceTests(
        @Autowired private val newReplyProcessorService: NewReplyProcessorService
) {
    @MockkBean
    private lateinit var answerRepository: AnswerRepository
    @MockkBean
    private lateinit var forumMailService: ForumMailService

    @Test
    fun `When execute then save before send email`() {
        val answer = mockk<Answer>()

        every { answerRepository.save(answer) } returns answer
        every { forumMailService.sendNewReplyMailAsync(answer) } just Runs

        newReplyProcessorService.execute(answer)

        verifyOrder {
            answerRepository.save(answer)
            forumMailService.sendNewReplyMailAsync(answer)
        }
    }
}