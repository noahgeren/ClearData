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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Business implements Serializable{

	private static final long serialVersionUID = 510579514259065788L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
		
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DailyReport> reports = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@ManyToOne
	@JoinColumn
	private City city;
	
	public Business() { }
	
	public Business(String name, Category category, City city) {
		this.name = name;
		this.category = category;
		this.city = city;
	}
	

}
