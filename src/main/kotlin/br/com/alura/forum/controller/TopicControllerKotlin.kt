package br.com.alura.forum.controller

import br.com.alura.forum.controller.dto.input.NewTopicInputDto
import br.com.alura.forum.controller.dto.input.TopicSearchInputDto
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDto
import br.com.alura.forum.controller.dto.output.TopicOutputDto
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.repository.CourseRepository
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.validator.NewTopicCustomValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/topics")
class TopicControllerKotlin(val topicRepository: TopicRepository, val courseRepository: CourseRepository) {
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

	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	fun createTopic(@RequestBody @Valid newTopicDto: NewTopicInputDto,
					@AuthenticationPrincipal loggedUser: User,
					uriBuilder: UriComponentsBuilder): ResponseEntity<TopicOutputDto> {
		val topic = newTopicDto.build(loggedUser, this.courseRepository)
		this.topicRepository.save(topic)

		val path = uriBuilder.path("/api/topics/{id}")
				.buildAndExpand(topic.id).toUri()

		return ResponseEntity.created(path).body(TopicOutputDto(topic))
	}

	@InitBinder("newTopicInputDto")
	fun initBinder(binder: WebDataBinder, @AuthenticationPrincipal loggedUser: User) {
		binder.addValidators(NewTopicCustomValidator(this.topicRepository, loggedUser))
	}
}