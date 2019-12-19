package br.com.alura.forum.controller

import br.com.alura.forum.repository.OpenTopicByCategoryRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/reports")
class ReportsController(private val openTopicByCategoryRepository: OpenTopicByCategoryRepository) {

    @GetMapping("/open-topics-by-category")
    fun showOpenTopicsByCategoryReport(model: Model): String {
        val openTopics = this.openTopicByCategoryRepository.findAllByCurrentMonth()

        model.addAttribute("openTopics", openTopics)

        return "report"
    }
}