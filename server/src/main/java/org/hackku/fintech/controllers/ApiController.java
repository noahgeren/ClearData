package org.hackku.fintech.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.Category;
import org.hackku.fintech.domains.DailyReport;
import org.hackku.fintech.domains.Prediction;
import org.hackku.fintech.domains.Weather;
import org.hackku.fintech.services.BusinessService;
import org.hackku.fintech.services.CategoryService;
import org.hackku.fintech.services.CityService;
import org.hackku.fintech.services.DailyReportService;
import org.hackku.fintech.services.PredictionService;
import org.hackku.fintech.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	BusinessService businessService;
	
	@Autowired
	DailyReportService reportService;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	PredictionService predictionService;

	@GetMapping("/categories/list")
	public List<Category> listCategories(){
		return categoryService.findAll();
	}
	
	@GetMapping("/predictions/week/{id}")
	public BigDecimal[] weekPredictions(@PathVariable Integer id){
		BigDecimal[] week = new BigDecimal[7];
		Arrays.fill(week, BigDecimal.ZERO);
		Business business = businessService.findById(id);
		if(business == null) return week;
		final LocalDate now = LocalDate.now();
		LocalDate day = now.minusDays((now.getDayOfWeek().getValue() % 7));
		for(int i = 0; i < week.length; i++, day=day.plusDays(1)) {
			week[i] = predictionService.predictDay(day, business);
		}
		return week;
	}
	
	@GetMapping("/averages/day/{id}")
	public BigDecimal[] dailyAverages(@PathVariable Integer id) {
		BigDecimal[] averages = new BigDecimal[3];
		Arrays.fill(averages, BigDecimal.ZERO);
		long count = 0;
		long sales = 0;
		boolean firstWeek = true;
		Business business = businessService.findById(id);
		if(business == null) return averages;
		List<DailyReport> reports = reportService.findByBusiness(business);
		for(DailyReport report: reports) {
			if(firstWeek) {
				averages[2] = averages[2].add(report.getIncome());
				if(report.getCreated().getDayOfWeek().getValue() == 7) firstWeek = false;
			}
			averages[0] = averages[0].add(report.getIncome());
			sales += report.getSales();
			count++;
		}
		BigDecimal total = averages[0];
		averages[0] = averages[0].divide(BigDecimal.valueOf((count == 0)? 1: count), RoundingMode.HALF_EVEN);
		averages[1] = total.divide(BigDecimal.valueOf((sales == 0)? 1: sales), RoundingMode.HALF_EVEN);
		return averages;
	}
	
	@GetMapping("/averages/week/{id}")
	public BigDecimal[] weeklyAverages(@PathVariable Integer id) {
		BigDecimal[] averages = new BigDecimal[7];
		Arrays.fill(averages, BigDecimal.ZERO);
		long[] counts = new long[7];
		Business business = businessService.findById(id);
		if(business == null) return averages;
		List<DailyReport> reports = reportService.findByBusiness(business);
		for(DailyReport report: reports) {
			int day = report.getCreated().getDayOfWeek().getValue() % 7;
			averages[day] = averages[day].add(report.getIncome());
			counts[day]++;
		}
		for(int i = 0; i < averages.length; i++) {
			averages[i] = averages[i].divide(BigDecimal.valueOf((counts[i] == 0)? 1: counts[i]), RoundingMode.HALF_EVEN);
		}
		return averages;
	}
	
	@GetMapping("/averages/year/{id}")
	public BigDecimal[] yearlyAverages(@PathVariable Integer id) {
		BigDecimal[] averages = new BigDecimal[12];
		Arrays.fill(averages, BigDecimal.ZERO);
		long[] counts = new long[12];
		Business business = businessService.findById(id);
		if(business == null) return averages;
		List<DailyReport> reports = reportService.findByBusiness(business);
		for(DailyReport report: reports) {
			int month = report.getCreated().getMonth().getValue() % 12;
			averages[month] = averages[month].add(report.getIncome());
			counts[month]++;
		}
		for(int i = 0; i < averages.length; i++) {
			averages[i] = averages[i].divide(BigDecimal.valueOf((counts[i] == 0)? 1: counts[i]), RoundingMode.HALF_EVEN);
		}
		return averages;
	}
	
	@GetMapping("/reports/week/{id}")
	public BigDecimal[] weekReports(@PathVariable Integer id){
		BigDecimal[] week = new BigDecimal[7];
		Arrays.fill(week, BigDecimal.ZERO);
		Business business = businessService.findById(id);
		if(business == null) return week;
		final LocalDate now = LocalDate.now();
		final LocalDate startOfWeek = now.minusDays((now.getDayOfWeek().getValue() % 7));
		final LocalDate endOfWeek = startOfWeek.plusDays(8);
		List<DailyReport> reports = reportService.findByBusiness(business);
		for(DailyReport report : reports) {
			if(report.getCreated().isAfter(startOfWeek.atStartOfDay()) && report.getCreated().isBefore(endOfWeek.atTime(23, 59))) {
				week[report.getCreated().getDayOfWeek().getValue() % 7] = report.getIncome();
			}
		}
		return week;
	}
	
	@GetMapping("/reports/year/{id}")
	public BigDecimal[] yearReports(@PathVariable Integer id) {
		BigDecimal[] year = new BigDecimal[12];
		long[] counts = new long[12];
		Arrays.fill(year, BigDecimal.ZERO);
		Business business = businessService.findById(id);
		if(business == null) return year;
		List<DailyReport> reports = reportService.findByBusiness(business);
		int currentYear = LocalDate.now().getYear();
		for(DailyReport report : reports) {
			if(report.getCreated().getYear() == currentYear) {
				year[report.getCreated().getMonth().getValue() - 1] = year[report.getCreated().getMonth().getValue() - 1].add(report.getIncome());
				counts[report.getCreated().getMonth().getValue() - 1]++;
			}
		}
		for(int i = 0; i < year.length; i++) {
			year[i] = year[i].divide(BigDecimal.valueOf((counts[i] == 0)? 1: counts[i]), RoundingMode.HALF_EVEN);
		}
		return year;
	}
	
	@GetMapping("/weather/list/{id}")
	public List<Weather> weather(@PathVariable Integer id){
		Business business = businessService.findById(id);
		return weatherService.findByCity(business.getCity());
	}
	
	@GetMapping("/reports/list/{id}")
	public List<DailyReport> reports(@PathVariable Integer id){
		Business business = businessService.findById(id);
		return reportService.findByBusiness(business);
	}
	
	@GetMapping("/predictions/list/{id}")
	public List<Prediction> predictions(@PathVariable Integer id){
		Business business = businessService.findById(id);
		return predictionService.findAllByBusiness(business);
	}
	
	@PostMapping("/reports/add/{id}")
	public boolean addReport(@PathVariable Integer id, @RequestBody DailyReport report) {
		Business business = businessService.findById(id);
		if(business == null) return false;
		report.setBusiness(business);
		report.setWeather(weatherService.searchAndSave(business.getCity()));
		report.setCreated(LocalDateTime.now());
		return reportService.save(report) != null;
	}
}
