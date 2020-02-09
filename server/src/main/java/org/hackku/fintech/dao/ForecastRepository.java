package org.hackku.fintech.dao;

import org.hackku.fintech.domains.Forecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, Long>{

}
