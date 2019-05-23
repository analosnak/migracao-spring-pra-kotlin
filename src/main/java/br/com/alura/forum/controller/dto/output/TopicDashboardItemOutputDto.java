package br.com.alura.forum.controller.dto.output;

import java.util.List;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData;
import br.com.alura.forum.vo.CategoryStatisticsData;

public class TopicDashboardItemOutputDto {

	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;
	
	public TopicDashboardItemOutputDto(Category category, CategoryStatisticsData numbers) {
		this.categoryName = category.getName();
		this.subcategories = category.getSubcategoryNames();
		this.allTopics = numbers.getAllTopics();
		this.lastWeekTopics = numbers.getLastWeekTopics();
		this.unansweredTopics = numbers.getUnansweredTopics();
	}

	public String getCategoryName() {
		return categoryName;
	}

	public List<String> getSubcategories() {
		return subcategories;
	}

	public int getAllTopics() {
		return allTopics;
	}

	public int getLastWeekTopics() {
		return lastWeekTopics;
	}

	public int getUnansweredTopics() {
		return unansweredTopics;
	}

	public static List<TopicDashboardItemOutputDto> listFromCategories(CategoriesAndTheirStatisticsData categoriesStatisticsData) {
			
		return categoriesStatisticsData
				.map((category, statData) -> new TopicDashboardItemOutputDto(category, statData));
	}

}
