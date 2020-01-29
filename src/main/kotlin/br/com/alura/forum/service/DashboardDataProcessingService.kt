package br.com.alura.forum.service

import br.com.alura.forum.repository.CategoryRepository
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class DashboardDataProcessingService(
        private val categoryRepository: CategoryRepository,
        private val categoryStatisticsService: CategoryStatisticsDataLoadingService
) {

    fun execute(lastWeek: Instant): CategoriesAndTheirStatisticsData {
        val categoriesAndTheirData = CategoriesAndTheirStatisticsData()

        val principalCategories = categoryRepository.findByCategoryIsNull()

        principalCategories.forEach {
            categoriesAndTheirData.add(it, categoryStatisticsService.load(it, lastWeek))
        }

        return categoriesAndTheirData
    }
}