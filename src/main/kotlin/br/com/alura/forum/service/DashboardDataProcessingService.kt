package br.com.alura.forum.service

import br.com.alura.forum.repository.CategoryRepository
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class DashboardDataProcessingService(
        private val categoryRepository: CategoryRepository,
        private val categoryStatisticsService: CategoryStatisticsDataLoadingService,
        private val categoriesAndTheirData: CategoriesAndTheirStatisticsData
) {

    fun execute(): CategoriesAndTheirStatisticsData {
        val principalCategories = categoryRepository.findByCategoryIsNull()

        val lastWeek = Instant.now().minus(7, ChronoUnit.DAYS)

        principalCategories.forEach {
            categoriesAndTheirData.add(it, categoryStatisticsService.load(it, lastWeek))
        }

        return categoriesAndTheirData
    }
}