package br.com.alura.forum.repository.setup;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class TopicRepositoryTestsSetup {

	private TestEntityManager testEntityManager;

	public TopicRepositoryTestsSetup(TestEntityManager testEntityManager) {
		this.testEntityManager = testEntityManager;
	}
	
    public void openTopicsByCategorySetup() {
        Category programacao = this.testEntityManager
                .persist(new Category("Programação"));
        Category front = this.testEntityManager
                .persist(new Category("Front-end"));

        Category javaWeb = this.testEntityManager
                .persist(new Category("Java Web", programacao));
        Category javaScript = this.testEntityManager
                .persist(new Category("JavaScript", front));

        Course fj27 = this.testEntityManager
                .persist(new Course("Spring Framework", javaWeb));
        Course fj21 = this.testEntityManager
                .persist(new Course("Servlet API e MVC", javaWeb));
        Course js46 = this.testEntityManager
                .persist(new Course("React", javaScript));

        User owner = this.testEntityManager
                .persist(new User("ana", "a@b.c", "senha"));
        Topic springTopic = new Topic("Tópico Spring", "Conteúdo do tópico", owner, fj27);
        Topic servletTopic = new Topic("Tópico Servlet", "Conteúdo do tópico", owner, fj21);
        Topic reactTopic = new Topic("Tópico React", "Conteúdo do tópico", owner, js46);

        this.testEntityManager.persist(springTopic);
        this.testEntityManager.persist(servletTopic);
        this.testEntityManager.persist(reactTopic);
    }


}
