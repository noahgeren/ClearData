package org.hackku.fintech.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class City implements Serializable{
	
	private static final long serialVersionUID = 3801616686302512687L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String key;
	
	private String state;
	
	private String postalCode;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Business> businesses = new ArrayList<>();
	
	public City() { }
	
	public City(String name, String key, String state, String postalCode) {
		this.name = name;
		this.key = key;
		this.state = state;
		this.postalCode = postalCode;
	}

}
