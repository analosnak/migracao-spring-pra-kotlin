package br.com.alura.forum.actuator.endpoints

import br.com.alura.forum.repository.TopicRepository
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Endpoint(id = "open-topics-by-category")
@Component
class UnansweredTopicsActuatorEndpoint(private val topicRepository: TopicRepository) {
    @ReadOperation
    fun execute() = topicRepository.findOpenTopicsByCategory()
}