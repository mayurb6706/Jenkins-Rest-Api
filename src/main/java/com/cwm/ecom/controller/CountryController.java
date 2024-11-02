package com.cwm.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.dao.CountryDao;
import com.cwm.ecom.model.Country;

@RestController
@RequestMapping("/api/country")
@CrossOrigin("http://localhost:4200")
public class CountryController {

	private final CountryDao dao;
	
	public CountryController(CountryDao dao) {
		this.dao=dao;
	}
	
	@GetMapping
	public ResponseEntity<List<Country>> getAllCountry(){
		List<Country> countries=new ArrayList<>();
		countries=this.dao.findAll();
		return ResponseEntity.ok(countries);
	}
}
