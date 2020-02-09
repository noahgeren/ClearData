package org.hackku.fintech.domains;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Prediction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private BigDecimal value;
	
	private LocalDate date;
	
	@ManyToOne
	@JsonIgnore
	private Business business;
	
	public Prediction() { }
	
	public Prediction(BigDecimal value, LocalDate date, Business business) {
		this.value = value;
		this.date = date;
		this.business = business;
	}

}
