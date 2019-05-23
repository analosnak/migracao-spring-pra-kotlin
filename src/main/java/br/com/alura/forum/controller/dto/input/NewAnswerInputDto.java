package br.com.alura.forum.controller.dto.input;

import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewAnswerInputDto {

    @NotBlank
    @Size(min = 5)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Answer build(Topic topic, User owner) {
        return new Answer(this.content, topic, owner);
    }
}
