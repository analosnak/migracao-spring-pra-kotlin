package br.com.alura.forum.vo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Category;

public class CategoriesAndTheirStatisticsData {

	private Map<Category, CategoryStatisticsData> categoriesAndTheirStats = new LinkedHashMap<>();

	public void add(Category category, CategoryStatisticsData statisticsData) {
		this.categoriesAndTheirStats.put(category, statisticsData);
	}
	
	public <R> List<R> map(BiFunction<Category, CategoryStatisticsData, R> extractorfunction) {
		
		return this.categoriesAndTheirStats.keySet().stream()
			.map(category -> extractorfunction.apply(category, this.categoriesAndTheirStats.get(category)))
			.collect(Collectors.toList());
	}

}
