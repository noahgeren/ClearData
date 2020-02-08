package org.hackku.fintech.dao;

import java.util.List;

import org.hackku.fintech.domains.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long>{
	
	public City findFirstByName(String name);
	
	public City findFirstByPostalCode(String postalCode);
	
	public List<City> findByStateOrderByStateAsc(String state);

}
