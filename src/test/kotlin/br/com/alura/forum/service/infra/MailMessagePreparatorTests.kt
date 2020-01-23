package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mail.javamail.MimeMessageHelper

class MailMessagePreparatorTests {
    @RelaxedMockK
    lateinit var messageHelper: MimeMessageHelper
    @MockK
    lateinit var newReplyMailFactory: NewReplyMailFactory

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `When prepare e-mail then put correct infos`() {
        val answer = mockk<Answer> {
            every { topic } returns mockk {
                every { owner } returns mockk {
                    every { email } returns "aluno@gmail.com"
                }
                every { shortDescription } returns "Descricao do tópico"
            }
        }

        every { newReplyMailFactory.generateNewReplyMailContent(answer) } returns "Conteúdo do e-mail"

        prepare(answer, messageHelper, newReplyMailFactory)

        verify {
            messageHelper.setTo("aluno@gmail.com")
            messageHelper.setSubject(match { it.contains("Descricao do tópico") })
            newReplyMailFactory.generateNewReplyMailContent(answer)
        }
    }
}