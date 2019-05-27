package br.com.alura.forum.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.model.OpenTopicByCategory;
import br.com.alura.forum.repository.setup.TopicRepositoryTestsSetup;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class TopicRepositoryTests {
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void shouldReturnOpenTopicsByCategory() {
		TopicRepositoryTestsSetup testsSetup = new TopicRepositoryTestsSetup(testEntityManager);
		testsSetup.openTopicsByCategorySetup();
		
		List<OpenTopicByCategory> openTopics = this.topicRepository.findOpenTopicsByCategory();
		
		assertThat(openTopics).isNotEmpty();
		assertThat(openTopics).hasSize(2);
		
		assertThat(openTopics.get(0).getCategoryName())
        .isEqualTo("Programação");
assertThat(openTopics.get(0).getTopicCount()).isEqualTo(2);

assertThat(openTopics.get(1).getCategoryName())
        .isEqualTo("Front-end");
assertThat(openTopics.get(1).getTopicCount()).isEqualTo(1);

	}
}
