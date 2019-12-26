package br.com.alura.forum.controller.dto.output

import br.com.alura.forum.model.Category
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import br.com.alura.forum.vo.CategoryStatisticsData

class TopicDashboardItemOutputDto(category: Category, numbers: CategoryStatisticsData) {
    val categoryName: String = category.name
    val subcategories: List<String> = category.subcategoryNames
    val allTopics: Int = numbers.allTopics
    val lastWeekTopics: Int = numbers.lastWeekTopics
    val unansweredTopics: Int = numbers.unansweredTopics

    companion object {
        fun listFromCategories(categoriesStatisticsData: CategoriesAndTheirStatisticsData) =
                categoriesStatisticsData.map{category: Category, statData: CategoryStatisticsData ->
                    TopicDashboardItemOutputDto(category, statData)
                }
    }
}