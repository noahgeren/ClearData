package org.hackku.fintech.domains;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class BusinessHours implements Serializable {
	
	private static final long serialVersionUID = -2466433934406920156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalTime open;
	
	private LocalTime close;
	
	@OneToOne
	private Business business;

}
