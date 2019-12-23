package br.com.alura.forum.repository

import br.com.alura.forum.model.OpenTopicByCategory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface OpenTopicByCategoryRepository : Repository<OpenTopicByCategory, Long> {
    fun saveAll(topics: Iterable<OpenTopicByCategory>)

    @Query("select t from OpenTopicByCategory t " +
            "where year(t.date) = year(current_date) " +
            "and month(t.date) = month(current_date)")
    fun findAllByCurrentMonth(): List<OpenTopicByCategory>
}