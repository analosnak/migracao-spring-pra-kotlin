package br.com.alura.forum.task

import br.com.alura.forum.repository.OpenTopicByCategoryRepository
import br.com.alura.forum.repository.TopicRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RegisterUnansweredTopicsTask(
        private val topicRepository: TopicRepository,
        private val openTopicByCategoryRepository: OpenTopicByCategoryRepository
) {
    @Scheduled(cron = "* * 20 * * *")
    fun execute() {
        val openTopicsByCategory = topicRepository.findOpenTopicsByCategory()
        openTopicByCategoryRepository.saveAll(openTopicsByCategory)
    }
}