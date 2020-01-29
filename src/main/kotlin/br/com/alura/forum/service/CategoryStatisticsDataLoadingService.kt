package br.com.alura.forum.service

import br.com.alura.forum.model.Category
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.vo.CategoryStatisticsData
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class CategoryStatisticsDataLoadingService(private val topicRepository: TopicRepository) {

    fun load(category: Category, startInstant: Instant): CategoryStatisticsData {
        category.id?.let {
        val allTopics = this.topicRepository.countTopicsByCategoryId(category.id)

        val lastWeekTopics = this.topicRepository
                .countLastWeekTopicsByCategoryId(category.id, startInstant)

        val unansweredTopics = this.topicRepository
                .countUnansweredTopicsByCategoryId(category.id)

            return CategoryStatisticsData(allTopics, lastWeekTopics, unansweredTopics)
        }
        error("O id da Categoria não pode ser nulo nesse momento")
    }
}