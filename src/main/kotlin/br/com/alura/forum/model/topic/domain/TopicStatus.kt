package br.com.alura.forum.model.topic.domain

import br.com.alura.forum.model.Answer

enum class TopicStatus : TopicState {
    NOT_ANSWERED {
        override fun registerNewReply(topic: Topic, newReply: Answer) {
            if (topic.owner != newReply.owner) topic.status = NOT_SOLVED

            topic.addAnswer(newReply)
        }

        override fun markAsSolved(topic: Topic) { topic.status = SOLVED }

        override fun close(topic: Topic) { topic.status = CLOSED }
    },

    NOT_SOLVED {
        override fun registerNewReply(topic: Topic, newReply: Answer) { topic.addAnswer(newReply) }

        override fun markAsSolved(topic: Topic) { topic.status = SOLVED }

        override fun close(topic: Topic) { topic.status = CLOSED }
    },

    SOLVED {
        override fun registerNewReply(topic: Topic, newReply: Answer) = topic.addAnswer(newReply)

        override fun close(topic: Topic) = throw RuntimeException("Essa dúvida já foi solucionada!")

        override fun markAsSolved(topic: Topic) =
            throw  RuntimeException("A dúvida já foi solucionada e deve ser mantida aberta para fins de registro!")
    },

    CLOSED {
        override fun registerNewReply(topic: Topic, newReply: Answer) =
            throw RuntimeException("Tópico fechado! Não é possível adicionar novas respostas")

        override fun close(topic: Topic) = throw RuntimeException("Essa dúvida já está fechada!")

        override fun markAsSolved(topic: Topic) = throw RuntimeException("A dúvida já está fechada!")
    }
}