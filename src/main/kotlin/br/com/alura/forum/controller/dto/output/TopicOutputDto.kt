package br.com.alura.forum.controller.dto.output

import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.model.topic.domain.TopicStatus
import java.time.Instant

class TopicOutputDto(topic: Topic) {
    val id: Long
    val shortDescription: String
    val content: String
    val status: TopicStatus
    val numberOfResponses: Int
    val creationInstant: Instant
    val lastUpdate: Instant
    val ownerName: String
    val courseName: String
    val subcategoryName: String
    val categoryName: String
    private val answers: MutableList<AnswerOutputDto> = ArrayList()

    fun getAnswers(): List<AnswerOutputDto> {
        return this.answers
    }

    init {
        this.id = topic.id
        this.shortDescription = topic.shortDescription
        print(topic.content)
        this.content = topic.content
        this.status = topic.status
        this.numberOfResponses = topic.numberOfAnswers
        this.creationInstant = topic.creationInstant
        this.lastUpdate = topic.lastUpdate
        this.ownerName = topic.owner.name
        this.courseName = topic.course.name
        this.subcategoryName = topic.course.subcategoryName
        this.categoryName = topic.course.categoryName

        val answers = AnswerOutputDto.listFromAnswers(topic.answers)
        this.answers.addAll(answers)
    }
}