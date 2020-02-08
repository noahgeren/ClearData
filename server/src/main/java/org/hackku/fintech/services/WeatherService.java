package org.hackku.fintech.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hackku.fintech.dao.WeatherRepository;
import org.hackku.fintech.domains.City;
import org.hackku.fintech.domains.Weather;
import org.hackku.fintech.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
	
	@Autowired
	WeatherRepository weatherRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${ACCUWEATHER_KEY}")
	private String apiKey;
	
	@SuppressWarnings("unchecked")
	public Weather searchApiByCity(City city) {
		if(city == null || city.getKey() == null) return null;
		Map<String, String> params = new HashMap<>();
		params.put("apikey", apiKey);
		params.put("details", "true");
		try {
			RequestEntity<Void> request = RequestEntity
					.get(
						URI.create(new StringBuilder("http://dataservice.accuweather.com/currentconditions/v1/")
									.append(city.getKey())
									.append("/historical/24") 
									.append(HelperUtil.getParameterUrlEncoding(params)).toString()))
					.accept(MediaType.APPLICATION_JSON)
					.build();
			List<Map<String, Object>> results = restTemplate.exchange(request, new ParameterizedTypeReference<List<Map<String, Object>>>(){}).getBody();
			if(results == null || results.size() == 0) return null;
			Map<String, Object> weatherData = results.get(0);
			Map<String, Object> precipitationData = (Map<String, Object>) weatherData.get("PrecipitationSummary");
			precipitationData = (Map<String, Object>) precipitationData.get("Past12Hours");
			precipitationData = (Map<String, Object>) precipitationData.get("Imperial");
			Map<String, Object> tempData = (Map<String, Object>) weatherData.get("TemperatureSummary");
			tempData = (Map<String, Object>) tempData.get("Past12HourRange");
			Map<String, Object> tempMin = (Map<String, Object>) tempData.get("Minimum");
			tempMin = (Map<String, Object>) tempMin.get("Imperial");
			Map<String, Object> tempMax = (Map<String, Object>) tempData.get("Maximum");
			tempMax = (Map<String, Object>) tempMax.get("Imperial");
			Weather weather = new Weather();
			weather.setCity(city);
			weather.setWeatherText((String) weatherData.get("WeatherText"));
			weather.setWeatherIcon((Integer) weatherData.get("WeatherIcon"));
			weather.setHasPrecipitation((Boolean) weatherData.get("HasPrecipitation"));
			weather.setPrecipitationType((String) weatherData.get("PrecipitationType"));
			weather.setPrecipitationAmount((Double) precipitationData.get("Value"));
			weather.setPrecipitationUnit((String) precipitationData.get("Unit"));
			weather.setMinTemperature((Double) tempMin.get("Value"));
			weather.setMaxTemperature((Double) tempMax.get("Value"));
			return weather;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
