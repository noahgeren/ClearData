package org.hackku.fintech.dao;

import java.util.List;

import org.hackku.fintech.domains.City;
import org.hackku.fintech.domains.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long>{

	public List<Weather> findByCityOrderByCreatedDesc(City city);
	
}
