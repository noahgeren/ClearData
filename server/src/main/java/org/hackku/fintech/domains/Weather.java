package org.hackku.fintech.domains;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Weather implements Serializable{
	
	private static final long serialVersionUID = 3553760284922069462L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
		
	private String weatherText;
	
	private Integer weatherIcon;
	
	private boolean hasPrecipitation;
	
	private String precipitationType;
	
	private Double precipitationAmount;
	
	private String precipitationUnit;
		
	private Double maxTemperature;
	
	private Double minTemperature;
	
	@ManyToOne
	@JsonIgnore
	private City city;
	
	@CreatedDate
	private LocalDateTime created;
	
	public Weather() { }
	
	public Weather(String weatherText, int weatherIcon, boolean hasPrecipitation,
					double precipitationAmount, String precipitationType, String precipitationUnit, 
					double maxTemperature, double minTemperature, City city, LocalDateTime created) {
		this.weatherText = weatherText;
		this.weatherIcon = weatherIcon;
		this.hasPrecipitation = hasPrecipitation;
		this.precipitationType = precipitationType;
		this.precipitationAmount = precipitationAmount;
		this.precipitationUnit = precipitationUnit;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.city = city;
		this.created = created;
	}

}
