package org.hackku.fintech;

import org.hackku.fintech.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{
	
	@Autowired
	CategoryService categoryService;

	@Override
	public void run(String... args) throws Exception {
		categoryService.addAll("Restaurant", "Convenient Store");
	}

}
