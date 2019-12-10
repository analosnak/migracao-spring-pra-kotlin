package br.com.alura.forum.model.topic.domain

import br.com.alura.forum.model.Answer

interface TopicState {
    fun registerNewReply(topic: Topic, newReply: Answer)
    fun markAsSolved(topic:Topic)
    fun close(topic: Topic)
}