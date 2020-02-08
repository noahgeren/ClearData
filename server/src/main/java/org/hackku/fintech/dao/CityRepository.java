package org.hackku.fintech.dao;

import org.hackku.fintech.domains.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long>{

}
