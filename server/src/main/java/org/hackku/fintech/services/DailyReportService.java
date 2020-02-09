package org.hackku.fintech.services;

import java.util.ArrayList;
import java.util.List;

import org.hackku.fintech.dao.DailyReportRepository;
import org.hackku.fintech.domains.Business;
import org.hackku.fintech.domains.DailyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyReportService {
	
	@Autowired
	DailyReportRepository reportRepo;
	
	public List<DailyReport> saveAll(List<DailyReport> reports){
		if(reports == null) return new ArrayList<>();
		return (List<DailyReport>) reportRepo.saveAll(reports);
	}
	
	public List<DailyReport> findByBusiness(Business business){
		if(business == null) return new ArrayList<>();
		return reportRepo.findByBusinessOrderByCreatedDesc(business);
	}

}
