package br.com.alura.forum.controller

import br.com.alura.forum.controller.dto.input.NewAnswerInputDto
import br.com.alura.forum.controller.dto.output.AnswerOutputDto
import br.com.alura.forum.model.User
import br.com.alura.forum.repository.TopicRepository
import br.com.alura.forum.service.NewReplyProcessorService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@Controller
@RequestMapping("/api/topics/{topicId}/answers")
class AnswerController(private val topicRepository: TopicRepository,
                       private val newReplyProcessorService: NewReplyProcessorService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                produces = [MediaType.APPLICATION_JSON_VALUE])
    fun answerTopic(@PathVariable topicId: Long,
                    @Valid @RequestBody newAnswerInputDto: NewAnswerInputDto,
                    @AuthenticationPrincipal loggedUser: User,
                    uriBuilder: UriComponentsBuilder): ResponseEntity<AnswerOutputDto> {

        val topic = this.topicRepository.findById(topicId)
        val answer = newAnswerInputDto.build(topic, loggedUser)

        newReplyProcessorService.execute(answer)

        val path = uriBuilder
                .path("/api/topics/{topicId}/answers/{answer}")
                .buildAndExpand(topicId, answer.id)
                .toUri()

        return ResponseEntity.created(path).body(AnswerOutputDto(answer))
    }
}