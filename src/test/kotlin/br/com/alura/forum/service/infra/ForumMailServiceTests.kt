package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessagePreparator

class ForumMailServiceTests {

    @Test
    fun `When sendNewReplyMailAsync then send email`() {
        val answer = mockk<Answer> {
            every { topic } returns mockk {
                every { owner } returns mockk {
                    every { email } returns "aluno@gmail.com"
                }
                every { shortDescription } returns "Descricao do tópico"
            }
        }

        val mailSender = mockk<JavaMailSender>()
        every { mailSender.send(any<MimeMessagePreparator>()) } just Runs

        val newReplyMailFactory = mockk<NewReplyMailFactory>()

        val forumMailService = ForumMailService(mailSender, newReplyMailFactory)
        forumMailService.sendNewReplyMailAsync(answer)

        verify { mailSender.send(any<MimeMessagePreparator>()) }
    }
}