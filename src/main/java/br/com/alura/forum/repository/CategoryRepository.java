package br.com.alura.forum.repository;

import java.util.List;

import br.com.alura.forum.model.Category;
import org.springframework.data.repository.Repository;

public interface CategoryRepository extends Repository<Category, Long> {

	List<Category> findByCategoryIsNull();

	Category findByName(String string);
}
