package br.com.alura.forum.service

import br.com.alura.forum.model.Category
import br.com.alura.forum.repository.CategoryRepository
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData
import br.com.alura.forum.vo.CategoryStatisticsData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DashboardDataProcessingServiceTests{
    @MockK
    private lateinit var categoryRepository: CategoryRepository
    @MockK
    private lateinit var categoryStatisticsService: CategoryStatisticsDataLoadingService
    @RelaxedMockK
    private lateinit var categoriesAndTheirData: CategoriesAndTheirStatisticsData

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

        val programacaoStatisticsData = CategoryStatisticsData(5, 1, 5)
        every { categoryStatisticsService.load(programacao, any()) } returns programacaoStatisticsData

        val frontStatisticsData = CategoryStatisticsData(3, 2, 0)
        every { categoryStatisticsService.load(front, any()) } returns frontStatisticsData

        val mobileStatisticsData = CategoryStatisticsData(2, 0, 1)
        every { categoryStatisticsService.load(mobile, any()) } returns mobileStatisticsData

        val dashboardService = DashboardDataProcessingService(categoryRepository, categoryStatisticsService, categoriesAndTheirData)
        val categoriesAndTheirData = dashboardService.execute()

        verify {
            categoriesAndTheirData.add(programacao, programacaoStatisticsData)
            categoriesAndTheirData.add(front, frontStatisticsData)
            categoriesAndTheirData.add(mobile, mobileStatisticsData)
        }
    }
}