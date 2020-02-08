package org.hackku.fintech.services;

import org.hackku.fintech.dao.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepo;

}
