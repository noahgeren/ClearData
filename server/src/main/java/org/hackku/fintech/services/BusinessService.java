package org.hackku.fintech.services;

import org.hackku.fintech.dao.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
	
	@Autowired
	BusinessRepository businessRepo;

}
