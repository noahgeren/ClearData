package org.hackku.fintech.services;

import java.util.List;

import org.hackku.fintech.dao.CityRepository;
import org.hackku.fintech.domains.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepo;
	
	public City findById(long id) {
		return cityRepo.findById(id).orElse(null);
	}
	
	public City findByName(String name) {
		if(name == null) return null;
		return cityRepo.findFirstByName(name);
	}
	
	public List<City> findByState(String state){
		return cityRepo.findByStateOrderByStateAsc(state);
	}

}
