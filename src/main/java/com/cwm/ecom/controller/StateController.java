package com.cwm.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.dao.StateDao;
import com.cwm.ecom.model.State;

@RestController
@RequestMapping("/api/state")
@CrossOrigin("http://localhost:4200")
public class StateController {

	private final StateDao dao;
	
	public StateController(StateDao dao) {
		this.dao=dao;
	}
	
	@GetMapping("/country")
	public ResponseEntity<List<State>> getStateByCounty(@RequestParam int country){
		List<State> states=new ArrayList<>();
		states=this.dao.findByCountryId(country);
		return ResponseEntity.ok(states);
	}
}
