package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.forum.model.OpenTopicByCategory;
import br.com.alura.forum.repository.OpenTopicByCategoryRepository;

@Controller
@RequestMapping("/admin/reports")
public class ReportsController {
	@Autowired
	private OpenTopicByCategoryRepository openTopicByCategoryReport;

	@GetMapping("/open-topics-by-category")
	public String showOpenTopicsByCategoryReport(Model model) {
		List<OpenTopicByCategory> openTopics = 
				this.openTopicByCategoryReport.findAllByCurrentMonth();
		openTopics.forEach(topic -> {
			
			System.out.println(topic.getId());
			System.out.println(topic.getCategoryName());
		});
		model.addAttribute("openTopics", openTopics);
		
		return "report";
	}
}
