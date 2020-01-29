package br.com.alura.forum.service

import br.com.alura.forum.model.Category
import br.com.alura.forum.repository.CategoryRepository
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import br.com.alura.forum.vo.CategoryStatisticsData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class DashboardDataProcessingServiceTests{
    @MockK
    private lateinit var categoryRepository: CategoryRepository
    @MockK
    private lateinit var categoryStatisticsService: CategoryStatisticsDataLoadingService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `When execute then return CategoriesAndTheirStatisticsData`() {
        val programacao = Category("Programação")
        val front = Category("Front")
        val mobile = Category("Mobile")

        every { categoryRepository.findByCategoryIsNull() } returns listOf(
                programacao,
                front,
                mobile
        )

        val lastWeek = Instant.now().minus(7, ChronoUnit.DAYS)

        every { categoryStatisticsService.load(any(), lastWeek) } returns CategoryStatisticsData(5, 1, 5)

        val dashboardService = DashboardDataProcessingService(categoryRepository, categoryStatisticsService)
        val categoriesAndTheirData = dashboardService.execute(lastWeek)

        assertNotNull(categoriesAndTheirData)

        verify (exactly = 1) {
            categoryStatisticsService.load(programacao, lastWeek)
            categoryStatisticsService.load(front, lastWeek)
            categoryStatisticsService.load(mobile, lastWeek)
        }
    }
}