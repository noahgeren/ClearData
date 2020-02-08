package org.hackku.fintech.dao;

import org.hackku.fintech.domains.Business;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Long>{

}
