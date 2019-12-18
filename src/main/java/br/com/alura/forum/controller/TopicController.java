package br.com.alura.forum.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import br.com.alura.forum.controller.dto.output.TopicDashboardItemOutputDto;
import br.com.alura.forum.service.DashboardDataProcessingService;
import br.com.alura.forum.vo.CategoriesAndTheirStatisticsData;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
	
	@Autowired
	private DashboardDataProcessingService dashboardDataProcessingService;

 
    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TopicDashboardItemOutputDto> getDashboardInfo() {
    
        CategoriesAndTheirStatisticsData categoriesStatisticsData = this.dashboardDataProcessingService.execute();
        return TopicDashboardItemOutputDto.listFromCategories(categoriesStatisticsData);
    		
    }

}
