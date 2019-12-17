package br.com.alura.forum.controller

import br.com.alura.forum.controller.dto.input.TopicSearchInputDto
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDto
import br.com.alura.forum.controller.dto.output.TopicOutputDto
import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.repository.TopicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topics")
class TopicControllerKotlin(val topicRepository: TopicRepository) {
	@GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
	fun getTopicDetails(@PathVariable id: Long) = TopicOutputDto(this.topicRepository.findById(id))

	@GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
	fun listTopics(
			topicSearch: TopicSearchInputDto,
			@PageableDefault(sort = ["creationInstant"], direction = Sort.Direction.DESC) pageRequest: Pageable
	): Page<TopicBriefOutputDto> {
		val topicSearchSpecification: Specification<Topic> = topicSearch.build()
		val topicsPage = this.topicRepository.findAll(topicSearchSpecification, pageRequest)

		return TopicBriefOutputDto.listFromTopics(topicsPage)
	}
}