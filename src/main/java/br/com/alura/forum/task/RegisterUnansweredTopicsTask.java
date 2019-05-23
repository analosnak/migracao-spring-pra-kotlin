package br.com.alura.forum.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.alura.forum.model.OpenTopicByCategory;
import br.com.alura.forum.repository.OpenTopicByCategoryRepository;
import br.com.alura.forum.repository.TopicRepository;

public class RegisterUnansweredTopicsTask {
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private OpenTopicByCategoryRepository openTopicByCategoryRepository;

	public void execute() {
		List<OpenTopicByCategory> topics = this.topicRepository.findOpenTopicsByCategory();
		this.openTopicByCategoryRepository.saveAll(topics);
	}
}
