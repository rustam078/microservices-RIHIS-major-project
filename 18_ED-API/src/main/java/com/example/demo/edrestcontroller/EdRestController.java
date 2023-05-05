package com.example.demo.edrestcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.binding.EligResponse;
import com.example.demo.service.EdServiceImpl;

@RestController
public class EdRestController {

	@Autowired
	private EdServiceImpl edservice;
	
	@GetMapping("/eligibility/{casenum}")
	public ResponseEntity<EligResponse> determine(@PathVariable Long casenum){
		EligResponse response = edservice.determineEligibility(casenum);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
