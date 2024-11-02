package com.cwm.ecom.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String code;
	private String name;
	
	@OneToMany(mappedBy = "country")
	private Set<State> states;
}
