package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.binding.CitizenApp;
import com.example.demo.service.ArServiceImpl;

@RestController
public class ARController {

	@Autowired
	private ArServiceImpl arservice;
	
	@PostMapping("/citizenApp")
	public ResponseEntity<String> registerArapp(@RequestBody CitizenApp app){
		String response = arservice.registerArappln(app);
		if(response!=null) {
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
		}
	}
}
