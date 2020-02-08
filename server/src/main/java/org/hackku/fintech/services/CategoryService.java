package org.hackku.fintech.services;

import org.hackku.fintech.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepo;

}
