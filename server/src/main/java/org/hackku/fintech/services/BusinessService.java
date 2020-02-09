package org.hackku.fintech.services;

import org.hackku.fintech.dao.BusinessRepository;
import org.hackku.fintech.domains.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
	
	@Autowired
	BusinessRepository businessRepo;
	
	public Business save(Business business) {
		if(business == null) return null;
		return businessRepo.save(business);
	}
	
	public Business findById(long id) {
		return businessRepo.findById(id).orElse(null);
	}

}
