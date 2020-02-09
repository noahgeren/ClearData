package org.hackku.fintech.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hackku.fintech.dao.PredictionRepository;
import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.DailyReport;
import org.hackku.fintech.domains.Forecast;
import org.hackku.fintech.domains.Prediction;
import org.hackku.fintech.util.PolyTrendLine;
import org.hackku.fintech.util.TrendLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {
	
	@Autowired
	PredictionRepository predictionRepo;
	
	@Autowired
	DailyReportService reportService;
	
	@Autowired
	ForecastService forecastService;
	
	public List<Prediction> findAllByBusiness(Business business){
		if(business == null) return new ArrayList<>();
		Prediction latest = predictionRepo.findFirstByBusinessOrderByIdDesc(business);
		LocalDate latestDate;
		if(latest == null) {
			latestDate = LocalDate.now().minusDays(1);
		}else {
			latestDate = latest.getDate();
		}
		if(latestDate.isBefore(LocalDate.now().plusDays(4))) {
			for(LocalDate date = (latestDate.isBefore(LocalDate.now()) ? LocalDate.now() : latestDate.plusDays(1)); 
					date.isBefore(LocalDate.now().plusDays(4)); date=date.plusDays(1)) {
				predictionRepo.save(new Prediction(predictDay(date, business), date, business));
			}
		}
		return predictionRepo.findByBusinessOrderByIdDesc(business);
	}
	


	public BigDecimal predictDay(LocalDate date, Business business) {
		List<DailyReport> reports = reportService.findByBusiness(business);
		List<Double> dayOfWeekIncomes = new ArrayList<>();
		for(DailyReport report : reports) {
			if(report.getCreated().getDayOfWeek().equals(date.getDayOfWeek())) {
				dayOfWeekIncomes.add(report.getIncome().doubleValue());
			}
		}
		double[] incomes = dayOfWeekIncomes.stream().mapToDouble(i -> i).toArray();
		double[] x = new double[incomes.length];
		for(int i =1; i < x.length; i++) {x[i] = i;}
		TrendLine t = new PolyTrendLine(5);
		t.setValues(incomes, x);
		double predictionValue = t.predict(x.length+1);
		Forecast forecast = forecastService.findOrSearch(business.getCity(), date);
		if(forecast != null) {
			predictionValue = predictionValue*(temperatureMultiplier(forecast.getMaxTemperature(),60,40))*(weatherMultiplier(forecast.getWeatherIcon(),forecast.getPrecipitationType(),forecast.getPrecipitationIntensity()));
		}
		BigDecimal endValue = new BigDecimal(predictionValue);
		return endValue;
	}
	
	public double temperatureMultiplier(double temperature, double mean, double sigma) {
		return Math.pow(Math.exp(-(((temperature - mean) * (temperature - mean)) / ((2 * sigma * sigma)))), 1 / (sigma * Math.sqrt(2 * Math.PI)));
	}
	
	public double weatherMultiplier(int weatherIcon, String precipitationType, String precipitationIntensity) {
		double weatherTypeMultiplier = 0;
		double precipitationTypeMultiplier = 0;
		double precipiationAmountMultiplier = 1;
		
		
		if(weatherIcon >= 1 && weatherIcon <= 5) {
			weatherTypeMultiplier = 1;
		}
		else if(weatherIcon >= 6 && weatherIcon <= 11) {
			weatherTypeMultiplier = 0.999;
		}
		else if(weatherIcon >= 12 && weatherIcon <= 14) {
			weatherTypeMultiplier = 0.98;
		}
		else if(weatherIcon >= 15 && weatherIcon <= 17) {
			weatherTypeMultiplier = 0.85;
		}
		else if(weatherIcon >= 18 && weatherIcon <= 21) {
			weatherTypeMultiplier = 0.9;
		}
		else if(weatherIcon >= 22 && weatherIcon <= 23) {
			weatherTypeMultiplier = 0.95;
		}
		else if(weatherIcon >= 24 && weatherIcon <= 25) {
			weatherTypeMultiplier = 0.85;
		}
		else if(weatherIcon >= 26 && weatherIcon <= 29) {
			weatherTypeMultiplier = 0.95;
		}
		else if(weatherIcon >= 30 && weatherIcon <= 30) {
			weatherTypeMultiplier = 0.99;
		}
		else if(weatherIcon >= 31 && weatherIcon <= 31) {
			weatherTypeMultiplier = 0.99;
		}
		else if(weatherIcon >= 32 && weatherIcon <= 32) {
			weatherTypeMultiplier = 0.99;
		}
		else if(weatherIcon >= 33 && weatherIcon <= 36) {
			weatherTypeMultiplier = 1;
		}
		else if(weatherIcon >= 37 && weatherIcon <= 38) {
			weatherTypeMultiplier = 0.99;
		}
		else if(weatherIcon >= 39 && weatherIcon <= 42) {
			weatherTypeMultiplier = 0.85;
		}
		else if(weatherIcon >= 43 && weatherIcon <= 44) {
			weatherTypeMultiplier = 0.95;
		}
		
		
		if("rain".equals(precipitationType)) {
			precipitationTypeMultiplier = 0.96;
		}
		else if("snow".equals(precipitationType)) {
			precipitationTypeMultiplier = 0.93;
		}
		else if("ice".equals(precipitationType)) {
			precipitationTypeMultiplier = 0.9;
		}
		else if("mixed".equals(precipitationType)) {
			precipitationTypeMultiplier = 0.85;
		}
		else {
			precipitationTypeMultiplier = 1;
		}
			
		if("Light".equals(precipitationIntensity)) {
			precipiationAmountMultiplier = 0.95;
		}
		else if("Moderate".equals(precipitationIntensity)) {
			precipiationAmountMultiplier = 0.85;
		}
		
		return (weatherTypeMultiplier*precipitationTypeMultiplier*precipiationAmountMultiplier);
	} 
}
