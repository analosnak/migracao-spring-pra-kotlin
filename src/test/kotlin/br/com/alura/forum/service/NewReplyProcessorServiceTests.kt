package br.com.alura.forum.service

import br.com.alura.forum.model.Answer
import br.com.alura.forum.repository.AnswerRepository
import br.com.alura.forum.service.infra.ForumMailService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NewReplyProcessorServiceTests {
    @MockK
    private lateinit var answerRepository: AnswerRepository
    @MockK
    private lateinit var forumMailService: ForumMailService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `When execute then save before send email`() {
        val answer = mockk<Answer>()

        every { answerRepository.save(answer) } returns answer
        every { forumMailService.sendNewReplyMailAsync(answer) } just Runs

        val newReplyProcessorService = NewReplyProcessorService(answerRepository, forumMailService)
        newReplyProcessorService.execute(answer)

        verifyOrder {
            answerRepository.save(answer)
            forumMailService.sendNewReplyMailAsync(answer)
        }
    }
}