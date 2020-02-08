package org.hackku.fintech.controllers;

import java.util.List;

import org.hackku.fintech.domains.Category;
import org.hackku.fintech.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories/list")
	public List<Category> listCategories(){
		return categoryService.findAll();
	}
	
}
