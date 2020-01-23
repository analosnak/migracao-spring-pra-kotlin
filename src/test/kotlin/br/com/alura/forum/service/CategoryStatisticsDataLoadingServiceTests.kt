package br.com.alura.forum.service

import br.com.alura.forum.model.Category
import br.com.alura.forum.repository.TopicRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CategoryStatisticsDataLoadingServiceTests {
    @MockK
    lateinit var topicRepository: TopicRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `When load then return CategoryStatisticsData`() {
        val category = mockk<Category> {
            every { id } returns 1
        }

        val allTopics = 5
        val lastWeekTopics = 1
        val unansweredTopics = 4

        every { topicRepository.countTopicsByCategoryId(1) } returns allTopics
        every { topicRepository.countLastWeekTopicsByCategoryId(1, any()) } returns lastWeekTopics
        every { topicRepository.countUnansweredTopicsByCategoryId(1) } returns unansweredTopics

        val categoryStatsService = CategoryStatisticsDataLoadingService(topicRepository)
        val statsData = categoryStatsService.load(category)

        assertEquals(allTopics, statsData.allTopics)
        assertEquals(lastWeekTopics, statsData.lastWeekTopics)
        assertEquals(unansweredTopics, statsData.unansweredTopics)

        verify {
            topicRepository.countLastWeekTopicsByCategoryId(1, any())
            topicRepository.countTopicsByCategoryId(1)
            topicRepository.countUnansweredTopicsByCategoryId(1)
        }
    }
}