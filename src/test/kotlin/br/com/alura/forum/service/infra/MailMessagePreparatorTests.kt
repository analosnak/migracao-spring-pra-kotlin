package br.com.alura.forum.service.infra

import br.com.alura.forum.infra.NewReplyMailFactory
import br.com.alura.forum.model.Answer
import br.com.alura.forum.model.Category
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
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
        val category = Category("Programação")
        val subcategory = Category("Java", category)
        val course = Course("Java Web", subcategory)

        val user = User("Aluno", "aluno@gmail.com", "123456")
        val topic = Topic("Descricao do tópico", "Conteúdo do tópico", user, course)
        val answer = Answer("Conteúdo da resposta", topic, user)

        every { newReplyMailFactory.generateNewReplyMailContent(answer) } returns "Conteúdo do e-mail"

        prepare(answer, messageHelper, newReplyMailFactory)

        verify {
            messageHelper.setTo("aluno@gmail.com")
            messageHelper.setSubject(match { it.contains("Descricao do tópico") })
            newReplyMailFactory.generateNewReplyMailContent(answer)
        }
    }
}