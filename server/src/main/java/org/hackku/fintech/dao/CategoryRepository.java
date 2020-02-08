package org.hackku.fintech.dao;

import org.hackku.fintech.domains.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

}
