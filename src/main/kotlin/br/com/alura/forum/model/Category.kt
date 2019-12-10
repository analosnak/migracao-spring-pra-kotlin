package br.com.alura.forum.model

import javax.persistence.*

@Entity
data class Category(val name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    var category: Category? = null
    private set

    @OneToMany(mappedBy = "category")
    private val _subcategories = listOf<Category>()

    val subcategoryNames: List<String>
    get() = _subcategories.map { it.name }

    constructor(name: String, category: Category) : this(name) {
        this.category = category
    }
}