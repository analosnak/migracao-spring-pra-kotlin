package br.com.alura.forum.controller.dto.input

import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.repository.CourseRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class NewTopicInputDto(@field:NotBlank @field:Size(min = 10) private val shortDescription: String,
                       @field:NotBlank @field:Size(min = 10) private val content: String,
                       @field:NotEmpty private val courseName: String) {
    fun build(owner: User, courseRepository: CourseRepository): Topic {
        val course = courseRepository.findByName(this.courseName)

        return Topic(this.shortDescription, this.content, owner, course)
    }
}