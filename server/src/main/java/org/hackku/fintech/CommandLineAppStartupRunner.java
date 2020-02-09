package org.hackku.fintech;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.Category;
import org.hackku.fintech.domains.City;
import org.hackku.fintech.domains.DailyReport;
import org.hackku.fintech.domains.Weather;
import org.hackku.fintech.services.BusinessService;
import org.hackku.fintech.services.CategoryService;
import org.hackku.fintech.services.CityService;
import org.hackku.fintech.services.DailyReportService;
import org.hackku.fintech.services.PredictionService;
import org.hackku.fintech.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	BusinessService businessService;
	
	@Autowired
	DailyReportService reportService;
	
	@Autowired
	PredictionService predictionService;
	
	private static final Random RAN = new Random(1337);
	
	private static final String[] WEATHER_TYPES = {"Rain", "Snow", "Ice", "Mixed"};
	
	private static final int[][] SUB_TYPES = {{12, 13, 14, 18, 15, 16, 17}, {19, 20, 21, 22, 23}, {24, 25}, {26, 29}, {1, 2, 3, 4, 5}};

	@Override
	public void run(String... args) throws Exception {
		List<Category> categories = categoryService.addAll("Restaurant", "Antique Shop", "Ice Cream Shop");
		City sgf = new City("Springfield", "329438", "MO", "65806");
		City lawrence = new City("Lawrence", "328846", "KS", "66044");
		sgf = cityService.save(sgf);
		lawrence = cityService.save(lawrence);
		Business diner = businessService.save(new Business("Joe's Diner", categories.get(0), sgf));
		Business antiques = businessService.save(new Business("Martha's Antiques", categories.get(1), lawrence));
		Business iceCream = businessService.save(new Business("Ol' Fashion Ice Cream", categories.get(2), lawrence));
		List<Weather> sgfWeather = createFakeWeather(LocalDate.of(2017, 2, 6), sgf, 
														0.08, new double[]{0.8, 0.9, 0.95, 1}, new double[] {0.1, 3},
														new double[] {40, 80}, 20, true);
		List<Weather> lawWeather = createFakeWeather(LocalDate.of(2017, 2, 6), lawrence,
														0.15, new double[] {0.5, 0.8, 0.9, 1}, new double[] {0.1, 6},
														new double[] {20, 50}, 10, false);
		sgfWeather = weatherService.saveAll(sgfWeather);
		lawWeather = weatherService.saveAll(lawWeather);
		List<DailyReport> dinerReports = createFakeReports(diner, sgfWeather, 500, 30, 
							new double[] {1.2, 0.5, 0.6, 0.8, 0.6, 0.9, 1.3},
							new double[] {0.9, 0.7, 0.4, 0.4}, 0);
		List<DailyReport> antiqueReports = createFakeReports(antiques, lawWeather, 300, 5,
							new double[] {1.2, 0.8, 0.7, 0.7, 0.6, 1.1, 1.3},
							new double[] {0.95, 0.9, 0.8, 0.8}, 0);
		List<DailyReport> iceCreamReports = createFakeReports(iceCream, lawWeather, 400, 40,
							new double[] {1.2, 0.8, 0.7, 0.7, 0.6, 1.1, 1.3},
							new double[] {0.9, 0.7, 0.3, 0.3}, 950);
		reportService.saveAll(dinerReports);
		reportService.saveAll(antiqueReports);
		reportService.saveAll(iceCreamReports);
	}
	
	private List<DailyReport> createFakeReports(Business business, List<Weather> weather, double baseIncome, 
			long sales, double[] weekdayMultipliers, double[] precipitationMultipliers, int start){
		List<DailyReport> reports = new ArrayList<>();
		for(int j = start; j < weather.size(); j++) {
			Weather day = weather.get(j);
			BigDecimal income = new BigDecimal(baseIncome);
			income = income.multiply(BigDecimal.valueOf( RAN.nextDouble() / 8 + .9375 ))
					.multiply(BigDecimal.valueOf(weekdayMultipliers[day.getCreated().getDayOfWeek().getValue() % 7]));
			BigDecimal weatherMultiplier = BigDecimal.valueOf(1);
			for(int i = 0; i < precipitationMultipliers.length; i++) {
				if(WEATHER_TYPES[i].equals(day.getPrecipitationType())) {
					weatherMultiplier = BigDecimal.valueOf(precipitationMultipliers[i]);
				}
			}
			income = income.multiply(weatherMultiplier);
			reports.add(new DailyReport(income, sales, business, day, day.getCreated().plusHours(1)));
			if(income.compareTo(BigDecimal.valueOf(baseIncome)) == 1) {
				sales*= Math.round(RAN.nextDouble() / 2 + .75);
			}
		}
		return reports;
	}
	
	private List<Weather> createFakeWeather(LocalDate startDate, City city, double precipitationChance, double[] precipitationTypeChances, 
											double[] precipitationRange, double[] temperatureRange, double dailyRange, boolean today){
		final String weatherText = "Clear", precipitationUnit = "in";
		List<Weather> weather = new ArrayList<>();
		for(LocalDate date = startDate; date.isBefore(LocalDate.now().plusDays(today ? 1 : 0)); date=date.plusDays(1)) {
			double amount = 0;
			int weatherIcon = 1;
			String type = null;
			boolean hasPrecipitation = RAN.nextDouble() <= precipitationChance;
			if(hasPrecipitation) {
				amount = RAN.nextDouble() * (precipitationRange[1] - precipitationRange[0]) + precipitationRange[0];
				double chance = RAN.nextDouble();
				for(int i = 0; i < precipitationTypeChances.length; i++) {
					if(chance <= precipitationTypeChances[i]) {
						type = WEATHER_TYPES[i];
						weatherIcon = SUB_TYPES[i][(int)(RAN.nextDouble() * SUB_TYPES[i].length)];
						break;
					}
				}
			}else {
				weatherIcon = SUB_TYPES[4][(int)(RAN.nextDouble() * SUB_TYPES[4].length)];
			}
			double min = (RAN.nextDouble() / 2) * (temperatureRange[1] - temperatureRange[0]) + temperatureRange[0], 
					max = min + (dailyRange * (RAN.nextDouble() + 0.5));
			weather.add(new Weather(weatherText, weatherIcon, hasPrecipitation, amount, type, precipitationUnit, max, min, city, date.atTime(19, 45)));
		}
		return weather;
	}

}
