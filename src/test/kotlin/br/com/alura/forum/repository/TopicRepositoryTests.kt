package br.com.alura.forum.repository

import br.com.alura.forum.repository.setup.TopicRepositoryTestsSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class TopicRepositoryTests @Autowired constructor(
        val topicRepository: TopicRepository,
        val testEntityManager: TestEntityManager
) {
    @Test
    fun `When findOpenTopicsByCategory then return Programação and Front-End`() {
        val testsSetup = TopicRepositoryTestsSetup(testEntityManager)
        testsSetup.openTopicsByCategorySetup()

        val openTopics = topicRepository.findOpenTopicsByCategory()

        assertThat(openTopics).isNotEmpty
        assertThat(openTopics).hasSize(2)

        assertThat(openTopics.first().categoryName)
                .isEqualTo("Programação")
        assertThat(openTopics.first().topicCount).isEqualTo(2)

        assertThat(openTopics[1].categoryName)
                .isEqualTo("Front-end")
        assertThat(openTopics[1].topicCount).isEqualTo(1)
    }
}