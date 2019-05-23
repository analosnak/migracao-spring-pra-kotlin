package br.com.alura.forum.model;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import br.com.alura.forum.model.topic.domain.Topic;

public class PossibleSpammer {

    private List<Topic> topics;

    public PossibleSpammer(List<Topic> topics) {
        this.topics = topics;
    }

    public boolean hasTopicLimitExceeded() {
        return this.topics.size() >= 4;
    }

    public long minutesToNextTopic(Instant from) {
        Instant instantOfTheOldestTopic = topics.get(0).getCreationInstant();

        return Duration.between(from, instantOfTheOldestTopic)
                .getSeconds() / 60;
    }
}
