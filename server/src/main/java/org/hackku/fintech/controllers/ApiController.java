package org.hackku.fintech.controllers;

import java.util.List;

import org.hackku.fintech.domains.Category;
import org.hackku.fintech.domains.City;
import org.hackku.fintech.services.CategoryService;
import org.hackku.fintech.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CityService cityService;

	@GetMapping("/categories/list")
	public List<Category> listCategories(){
		return categoryService.findAll();
	}
	
	@GetMapping("/cities/search")
	public City searchCities(@RequestParam String q) {
		return cityService.searchApi(q);
	}
	
}
