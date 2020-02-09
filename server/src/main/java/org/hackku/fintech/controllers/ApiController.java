package org.hackku.fintech.controllers;

import java.util.List;

import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.Category;
import org.hackku.fintech.domains.DailyReport;
import org.hackku.fintech.services.BusinessService;
import org.hackku.fintech.services.CategoryService;
import org.hackku.fintech.services.CityService;
import org.hackku.fintech.services.DailyReportService;
import org.hackku.fintech.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	BusinessService businessService;
	
	@Autowired
	DailyReportService reportService;
	
	@Autowired
	WeatherService weatherService;

	@GetMapping("/categories/list")
	public List<Category> listCategories(){
		return categoryService.findAll();
	}
	
	@GetMapping("/reports/list")
	public List<DailyReport> reports(){
		Business business = businessService.findById(1);
		return reportService.findByBusiness(business);
	}
}
