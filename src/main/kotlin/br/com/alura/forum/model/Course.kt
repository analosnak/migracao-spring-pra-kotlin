package br.com.alura.forum.model

import javax.persistence.*

@Entity
data class Course(val name: String,
             @ManyToOne val subcategory: Category) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val categoryName: String
        get() {
            val category = checkNotNull(subcategory.category) { "Esta já é uma categoria mãe" }

            return category.name
        }
//            get() = subcategory.category?.name ?: throw IllegalStateException("Esta já é uma categoria mãe")
}