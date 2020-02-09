package org.hackku.fintech.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hackku.fintech.dao.ForecastRepository;
import org.hackku.fintech.domains.City;
import org.hackku.fintech.domains.Forecast;
import org.hackku.fintech.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ForecastService {
	
	@Autowired
	ForecastRepository forecastRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${ACCUWEATHER_KEY}")
	private String apiKey;
	
	public Forecast findOrSearch(City city, LocalDate date) {
		Forecast forecast = forecastRepo.findFirstByCityAndDate(city, date);
		if(forecast != null) return forecast;
		List<Forecast> forecasts = searchApiByCity(city);
		if(forecasts == null) return null;
		forecasts = (List<Forecast>) forecastRepo.saveAll(forecasts);
		for(Forecast f : forecasts) {
			if(f.getDate().equals(date)) return forecast;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Forecast> searchApiByCity(City city){
		List<Forecast> forecasts = new ArrayList<>();
		if(city == null || city.getKey() == null) return forecasts;
		Map<String, String> params = new HashMap<>();
		params.put("apikey", apiKey);
		try {
			RequestEntity<Void> request = RequestEntity
					.get(
						URI.create(new StringBuilder("http://dataservice.accuweather.com/forecasts/v1/daily/5day/")
									.append(city.getKey())
									.append(HelperUtil.getParameterUrlEncoding(params)).toString()))
					.accept(MediaType.APPLICATION_JSON)
					.build();
			Map<String, Object> results = restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>(){}).getBody();
			List<Map<String, Object>> daily = (List<Map<String, Object>>) results.get("DailyForecasts");
			for(Map<String, Object> day : daily) {
				Map<String, Object> temp = (Map<String, Object>) day.get("Temperature");
				Map<String, Object> min = (Map<String, Object>) temp.get("Minimum");
				Map<String, Object> max = (Map<String, Object>) temp.get("Maximum");
				Map<String, Object> summary = (Map<String, Object>) day.get("Day");
				Forecast forecast = new Forecast();
				forecast.setCity(city);
				forecast.setMaxTemperature((Double) max.get("Value"));
				forecast.setMinTemperature((Double) min.get("Value"));
				forecast.setDate(LocalDate.parse(((String) day.get("Date")).substring(0, 10)));
				forecast.setWeatherText((String) summary.get("IconPhrase"));
				forecast.setWeatherIcon((Integer) summary.get("Icon"));
				forecast.setHasPrecipitation((boolean) summary.get("HasPrecipitation"));
				if(forecast.isHasPrecipitation()) {
					forecast.setPrecipitationType((String) summary.get("PrecipitationType"));
					forecast.setPrecipitationIntensity((String) summary.get("PrecipitationIntensity"));
				}
				forecasts.add(forecast);
			}
			return forecasts;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}
