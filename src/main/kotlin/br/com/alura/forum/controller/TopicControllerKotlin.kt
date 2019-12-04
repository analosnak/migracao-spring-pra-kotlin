package br.com.alura.forum.controller

import br.com.alura.forum.controller.dto.output.TopicOutputDto
import br.com.alura.forum.repository.TopicRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topics")
class TopicControllerKotlin(val topicRepository: TopicRepository) {
	@GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
	fun getTopicDetails(@PathVariable id: Long): TopicOutputDto {
		val foundTopic = this.topicRepository.findById(id)

		return TopicOutputDto(foundTopic)
	}
}