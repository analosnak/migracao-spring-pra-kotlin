package br.com.alura.forum.repository;

import java.util.List;

import br.com.alura.forum.model.OpenTopicByCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface OpenTopicByCategoryRepository extends Repository<OpenTopicByCategory, Long> {
	void saveAll(Iterable<OpenTopicByCategory> topics);

	@Query("select t from OpenTopicByCategory t " +
            "where year(t.date) = year(current_date) " +
            "and month(t.date) = month(current_date)")
	List<OpenTopicByCategory> findAllByCurrentMonth();
}
