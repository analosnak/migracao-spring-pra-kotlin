package br.com.alura.forum.model

import br.com.alura.forum.model.topic.domain.Topic
import java.time.Instant
import javax.persistence.*

@Entity
data class Answer(@Lob val content: String,
             @ManyToOne val topic: Topic,
             @ManyToOne val owner: User) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val creationTime: Instant = Instant.now()

    var solution: Boolean = false
    private set

    init {
        topic.registerNewReply(this)
    }

    fun markAsSolution() {
        this.solution = true
        this.topic.markAsSolved()
    }
}