package br.com.alura.forum.controller


import br.com.alura.forum.controller.dto.input.NewAnswerInputDto
import br.com.alura.forum.model.Category
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.repository.UserRepository
import br.com.alura.forum.security.jwt.TokenManager
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.util.UriTemplate
import java.net.URI
import javax.transaction.Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
class AnswerControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val authManager: AuthenticationManager,
    val tokenManager: TokenManager,
    val userRepository: UserRepository,
    val topicRepository: TopicRepository,
    val testEntityManager: TestEntityManager
) {
    private lateinit var jwt: String
    private lateinit var uri: URI

    @BeforeEach
    fun setup() {
            val rawPassword = "123456"
            val user = User("Aluno da Alura", "aluno@gmail.com", BCryptPasswordEncoder().encode(rawPassword))
            val persistedUser = userRepository.save(user)

            val category = Category("Front-End")
            val persistedCategory = testEntityManager.persist(category)

            val subcategory = Category("JavaScript", persistedCategory)
            val persistedSubcategory = testEntityManager.persist(subcategory)

            val course = Course("React", persistedSubcategory)
            val persistedCourse = testEntityManager.persist(course)

            val topic = Topic("Descrição do Tópico", "Conteúdo do Tópico", persistedUser, persistedCourse)
            val persistedTopic = topicRepository.save(topic)

            uri = UriTemplate(ENDPOINT).expand(persistedTopic.id)

            val authenticationToken = UsernamePasswordAuthenticationToken(user.username, rawPassword)
            val authentication = authManager.authenticate(authenticationToken)
            jwt = tokenManager.generateToken(authentication)
    }

    @Test
    fun `When create new answer then assert status code and content`() {
        val newAnswerInputDto = NewAnswerInputDto("Nao consigo subir servidor")

        mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(newAnswerInputDto)
            header("Authorization", "Bearer $jwt")
        }.andExpect {
            status { isCreated }
            content { string(containsString(newAnswerInputDto.content)) }
        }
    }

    companion object {
        private const val ENDPOINT = "/api/topics/{topicId}/answers"
    }
}