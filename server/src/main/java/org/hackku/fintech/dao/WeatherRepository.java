package org.hackku.fintech.dao;

import org.hackku.fintech.domains.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Long>{

}
