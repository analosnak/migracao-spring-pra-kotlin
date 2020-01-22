package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class ForumMailServiceTests @Autowired constructor(val forumMailService: ForumMailService) {
    @MockkBean
    private lateinit var mailSender: JavaMailSender
    @MockkBean
    private lateinit var newReplyMailFactory: NewReplyMailFactory

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

        every { newReplyMailFactory.generateNewReplyMailContent(answer) } returns "Conteúdo do e-mail"

        forumMailService.sendNewReplyMailAsync(answer)

        verify { mailSender.send(any<MimeMessagePreparator>()) }
        verify { newReplyMailFactory.generateNewReplyMailContent(answer) }
    }
}