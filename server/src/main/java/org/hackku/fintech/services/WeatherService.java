package org.hackku.fintech.services;

import org.hackku.fintech.dao.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
	
	@Autowired
	WeatherRepository weatherRepo;

}
