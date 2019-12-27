package br.com.alura.forum.repository

import br.com.alura.forum.model.Category
import org.springframework.data.repository.Repository

interface CategoryRepository: Repository<Category, Long> {
    fun findByCategoryIsNull(): List<Category>
    fun findByName(name: String): Category
}