package br.com.alura.forum.model

import javax.persistence.*

@Entity
data class Course(val name: String,
             @ManyToOne val subcategory: Category) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun getCategoryName(): String = subcategory.category.orElseThrow{IllegalStateException("Esta já é uma categoria mãe")}.name
}