package org.hackku.fintech.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hackku.fintech.dao.CityRepository;
import org.hackku.fintech.domains.City;
import org.hackku.fintech.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${ACCUWEATHER_KEY}")
	private String apiKey;
	
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
	
	public City save(City city) {
		if(city == null || city.getKey() == null) return null;
		return cityRepo.save(city);
	}
	
	@SuppressWarnings("unchecked")
	public City searchApi(String query) {
		if(query == null) return null;
		Map<String, String> params = new HashMap<>();
		params.put("apikey", apiKey);
		params.put("q", query);
		try {
			RequestEntity<Void> request = RequestEntity
					.get(URI.create("http://dataservice.accuweather.com/locations/v1/cities/search" + HelperUtil.getParameterUrlEncoding(params)))
					.accept(MediaType.APPLICATION_JSON)
					.build();
			List<Map<String, Object>> results = restTemplate.exchange(request, new ParameterizedTypeReference<List<Map<String, Object>>>(){}).getBody();
			if(results == null || results.size() == 0) return null;
			Map<String, Object> cityData = results.get(0);
			Map<String, Object> stateData = (Map<String, Object>) cityData.get("AdministrativeArea");
			City city = new City();
			city.setName((String) cityData.get("EnglishName"));
			city.setKey((String) cityData.get("Key"));
			city.setPostalCode((String) cityData.get("PrimaryPostalCode"));
			city.setState((String) stateData.get("ID"));
			return city;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public City searchApiAndSave(String q) {
		City city = searchApi(q);
		if(city == null) return null;
		return cityRepo.save(city);
	}

}
