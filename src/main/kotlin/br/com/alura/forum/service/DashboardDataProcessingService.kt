package br.com.alura.forum.service

import br.com.alura.forum.repository.CategoryRepository
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import org.springframework.stereotype.Service

@Service
class DashboardDataProcessingService(private val categoryRepository: CategoryRepository,
                                     private val categoryStatisticsService: CategoryStatisticsDataLoadingService) {

    fun execute(): CategoriesAndTheirStatisticsData {
        val principalCategories = this.categoryRepository.findByCategoryIsNull()
        val categoriesAndTheirData = CategoriesAndTheirStatisticsData()

        principalCategories.forEach {
            categoriesAndTheirData.add(it, this.categoryStatisticsService.load(it))
        }

        return categoriesAndTheirData
    }
}