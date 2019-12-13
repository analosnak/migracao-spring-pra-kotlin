package br.com.alura.forum.repository;

import br.com.alura.forum.model.Course;
import org.springframework.data.repository.Repository;

public interface CourseRepository extends Repository<Course, Long> {

	Course findByName(String courseName);
}
