package br.com.alura.forum.service

import br.com.alura.forum.model.Category
import br.com.alura.forum.repository.TopicRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CategoryStatisticsDataLoadingServiceTests @Autowired constructor(
        val categoryStatsService: CategoryStatisticsDataLoadingService
) {
    @MockkBean
    private lateinit var topicRepository: TopicRepository

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

        val statsData = categoryStatsService.load(category)

        assertEquals(allTopics, statsData.allTopics)
        assertEquals(lastWeekTopics, statsData.lastWeekTopics)
        assertEquals(unansweredTopics, statsData.unansweredTopics)
    }
}