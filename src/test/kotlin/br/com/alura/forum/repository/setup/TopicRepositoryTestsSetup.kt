package br.com.alura.forum.repository.setup

import br.com.alura.forum.model.Category
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

class TopicRepositoryTestsSetup(private val testEntityManager: TestEntityManager) {
    fun openTopicsByCategorySetup() {
        val programacao = testEntityManager.persist(
                Category("Programação"))
        val front = testEntityManager.persist(
                Category("Front-end"))

        val javaWeb = testEntityManager.persist(
                Category("Java Web", programacao))
        val javaScript = testEntityManager.persist(
                Category("JavaScript", front))

        val springCourse = testEntityManager.persist(
                Course("Spring Framework", javaWeb))
        val servletCourse = testEntityManager.persist(
                Course("Servlet API e MVC", javaWeb))
        val reactCourse = testEntityManager.persist(
                Course("React", javaScript))

        val owner = testEntityManager.persist(
                User("Ana", "ana@gmail.com", "123456"))

        val springTopic = Topic("Tópico Spring", "Conteúdo do tópico", owner, springCourse)
        val serlvetTopic = Topic("Tópico Servlet", "Conteúdo do tópico", owner, servletCourse)
        val reactTopic = Topic("Tópico React", "Conteúdo do tópico", owner, reactCourse)

        testEntityManager.persist(springTopic)
        testEntityManager.persist(serlvetTopic)
        testEntityManager.persist(reactTopic)
    }
}