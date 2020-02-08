package org.hackku.fintech.domains;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class DailyReport implements Serializable{
	
	private static final long serialVersionUID = -8058977916274582909L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private BigDecimal income;
	
	private long sales;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Business business;
	
	@ManyToOne
	private Weather weather;
	
	@CreatedDate
	private LocalDateTime created;

}
