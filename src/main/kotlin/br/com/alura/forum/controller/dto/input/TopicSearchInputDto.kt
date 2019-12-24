package br.com.alura.forum.controller.dto.input

import br.com.alura.forum.model.Category
import br.com.alura.forum.model.Course
import br.com.alura.forum.model.topic.domain.Topic
import br.com.alura.forum.model.topic.domain.TopicStatus
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class TopicSearchInputDto(private val status: TopicStatus?, private val categoryName: String?) {
    fun build() = Specification<Topic> { root: Root<Topic>, _, criteriaBuilder ->
        val predicates = mutableListOf<Predicate>()

        status?.let {
            predicates.add(criteriaBuilder.equal(root.get<TopicStatus>("status"), it))
        }

        categoryName?.let {
            val categoryNamePath = root.get<Course>("course")
                    .get<Category>("subcategory").get<Category>("category").get<String>("name")
            predicates.add(criteriaBuilder.equal(categoryNamePath, categoryName))
        }

        criteriaBuilder.and(*predicates.toTypedArray())
    }
}