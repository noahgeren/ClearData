package org.hackku.fintech.services;

import java.util.ArrayList;
import java.util.List;

import org.hackku.fintech.dao.CategoryRepository;
import org.hackku.fintech.domains.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	public Category findById(long id) {
		return categoryRepo.findById(id).orElse(null);
	}
	
	public List<Category> findAll(){
		return (List<Category>) categoryRepo.findAll();
	}
	
	public void addAll(String... names) {
		List<Category> categories = new ArrayList<>();
		for(String name : names) {
			Category category = new Category();
			category.setName(name);
			categories.add(category);
		}
		categoryRepo.saveAll(categories);
	}

}
