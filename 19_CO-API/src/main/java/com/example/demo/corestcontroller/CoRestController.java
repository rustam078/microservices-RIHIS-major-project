package com.example.demo.corestcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.coservice.CoService;
import com.example.demo.entity.CoResponse;

@RestController
public class CoRestController {

	@Autowired
	private CoService coservice;
	
	@GetMapping("/process")
	public CoResponse processTrigger()throws Exception{
		return coservice.processPendingTriggers();
	}
}
