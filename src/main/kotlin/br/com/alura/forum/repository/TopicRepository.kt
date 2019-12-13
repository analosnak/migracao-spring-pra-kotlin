package br.com.alura.forum.repository

import br.com.alura.forum.model.OpenTopicByCategory
import br.com.alura.forum.model.User
import br.com.alura.forum.model.topic.domain.Topic
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.time.Instant

interface TopicRepository : Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    fun findAll(): List<Topic>

    fun save(topic: Topic): Topic

    fun findById(id: Long): Topic

    @Query("SELECT count(topic) FROM Topic topic "
            + "JOIN topic.course course "
            + "JOIN course.subcategory subcategory "
            + "JOIN subcategory.category category "
            + "WHERE category.id = :categoryId")
    fun countTopicsByCategoryId(@Param("categoryId") categoryId: Long): Int

    @Query("SELECT count(topic) FROM Topic topic "
            + "JOIN topic.course course "
            + "JOIN course.subcategory subcategory "
            + "JOIN subcategory.category category "
            + "WHERE category.id = :categoryId AND topic.creationInstant > :lastWeek")
    fun countLastWeekTopicsByCategoryId(@Param("categoryId") categoryId: Long,
                                        @Param("lastWeek") lastWeek: Instant): Int

    @Query("SELECT count(topic) FROM Topic topic "
            + "JOIN topic.course course "
            + "JOIN course.subcategory subcategory "
            + "JOIN subcategory.category category "
            + "WHERE category.id = :categoryId AND topic.status = 'NOT_ANSWERED'")
    fun countUnansweredTopicsByCategoryId(@Param("categoryId") categoryId: Long): Int

    fun findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(owner: User, creationTime: Instant): List<Topic>

    @Query("select new br.com.alura.forum.model.OpenTopicByCategory(" +
            "t.course.subcategory.category.name as categoryName, " +
            "count(t) as topicCount, " +
            "now() as instant) from Topic t " +
            "where t.status = 'NOT_ANSWERED' " +
            "group by t.course.subcategory.category")
    fun findOpenTopicsByCategory(): List<OpenTopicByCategory>
}