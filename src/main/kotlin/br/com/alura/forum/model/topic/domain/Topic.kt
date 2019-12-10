package br.com.alura.forum.model.topic.domain

import br.com.alura.forum.model.Answer
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.User
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.springframework.util.Assert
import java.time.Instant
import javax.persistence.*
import javax.xml.ws.soap.MTOM

@Entity
data class Topic(val shortDescription: String,
            @Lob val content: String,
            @ManyToOne val owner: User,
            @ManyToOne val course: Course) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val creationInstant: Instant = Instant.now()
    val lastUpdate: Instant = Instant.now()
    @Enumerated(EnumType.STRING)
    var status: TopicStatus = TopicStatus.NOT_ANSWERED
    @OneToMany(mappedBy = "topic")
    @LazyCollection(LazyCollectionOption.EXTRA)
    val answers = listOf<Answer>()
    val numberOfAnswers: Int
        get() = answers.size

    fun addAnswer(answer: Answer) { answers + answer}

    fun registerNewReply(newReply: Answer) {
        Assert.notNull(newReply, "Nova resposta não pode ser nula")

        status.registerNewReply(this, newReply)
    }

    fun markAsSolved(solution: Answer) {
        Assert.notNull(solution, "A resposta de solução não pode ser nula")

        status.markAsSolved(this)
    }

    fun close() = status.close(this)
}