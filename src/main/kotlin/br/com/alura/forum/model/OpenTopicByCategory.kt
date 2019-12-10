package br.com.alura.forum.model

import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class OpenTopicByCategory(val categoryName: String, topicCount: Number, instant: Date) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val topicCount: Int = topicCount.toInt()

    val date: LocalDate = instant.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

}