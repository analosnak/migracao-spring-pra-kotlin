package br.com.alura.forum.repository;

import java.time.Instant;
import java.util.List;

import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {

	@Query("select t from Topic t")
	List<Topic> list();

	List<Topic> findAll();
	
	Topic save(Topic topic);

	Topic findById(Long id);

	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId")
	int countTopicsByCategoryId(@Param("categoryId") Long categoryId);

	
	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId AND topic.creationInstant > :lastWeek")
	int countLastWeekTopicsByCategoryId(@Param("categoryId") Long categoryId,
			@Param("lastWeek") Instant lastWeek);

	
	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId AND topic.status = 'NOT_ANSWERED'")
	int countUnansweredTopicsByCategoryId(@Param("categoryId") Long categoryId);

	List<Topic> findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(User owner, Instant creationTime);
}
