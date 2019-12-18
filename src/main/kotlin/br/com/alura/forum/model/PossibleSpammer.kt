package br.com.alura.forum.model

import br.com.alura.forum.model.topic.domain.Topic
import java.time.Duration
import java.time.Instant

class PossibleSpammer(private val topics: List<Topic>) {
    val hasTopicLimitExceeded = this.topics.size >= 4

    fun minutesToNextTopic(from: Instant): Long {
        val instantOfTheOldestTopic = this.topics[0].creationInstant

        return Duration.between(from, instantOfTheOldestTopic).seconds/60
    }
}