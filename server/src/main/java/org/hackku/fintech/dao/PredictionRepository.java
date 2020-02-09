package org.hackku.fintech.dao;

import java.util.List;

import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.Prediction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends CrudRepository<Prediction, Long> {

	public Prediction findFirstByBusinessOrderByIdDesc(Business business);
	
	public List<Prediction> findByBusinessOrderByIdDesc(Business business);
	
}
