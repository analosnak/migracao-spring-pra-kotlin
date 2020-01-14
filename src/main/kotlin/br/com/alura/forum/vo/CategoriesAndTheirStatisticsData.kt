package br.com.alura.forum.vo

import br.com.alura.forum.model.Category

class CategoriesAndTheirStatisticsData {
    private val categoriesAndTheirStats: MutableMap<Category, CategoryStatisticsData> = mutableMapOf()

    fun add(category: Category, statisticsData: CategoryStatisticsData) {
        this.categoriesAndTheirStats[category] = statisticsData
    }

    fun <R> map(extractorFunction: (category: Category, statData: CategoryStatisticsData) -> R): List<R?> =
            this.categoriesAndTheirStats.map { extractorFunction(it.key, it.value) }
}