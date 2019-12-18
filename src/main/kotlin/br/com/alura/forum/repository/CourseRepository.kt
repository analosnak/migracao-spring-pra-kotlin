package br.com.alura.forum.repository

import br.com.alura.forum.model.Course
import org.springframework.data.repository.Repository

interface CourseRepository : Repository<Course, Long> {
    fun findByName(courseName: String): Course
}