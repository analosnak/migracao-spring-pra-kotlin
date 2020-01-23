package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.mail.javamail.MimeMessageHelper

class MailMessagePreparatorTests {

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

        val messageHelper = mockk<MimeMessageHelper>(relaxed = true)

        val newReplyMailFactory = mockk<NewReplyMailFactory>()
        every { newReplyMailFactory.generateNewReplyMailContent(answer) } returns "Conteúdo do e-mail"

        prepare(answer, messageHelper, newReplyMailFactory)

        verify {
            messageHelper.setTo("aluno@gmail.com")
            messageHelper.setSubject(match { it.contains("Descricao do tópico") })
            newReplyMailFactory.generateNewReplyMailContent(answer)
        }
    }
}