package org.hackku.fintech.dao;

import java.time.LocalDate;

import org.hackku.fintech.domains.City;
import org.hackku.fintech.domains.Forecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, Long>{
	
	public Forecast findFirstByCityAndDate(City city, LocalDate date);

}
