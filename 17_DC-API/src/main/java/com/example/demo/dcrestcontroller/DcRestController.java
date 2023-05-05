package com.example.demo.dcrestcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.binding.Education;
import com.example.demo.binding.Income;
import com.example.demo.binding.PlanSelection;
import com.example.demo.binding.Summary;
import com.example.demo.binding.kidsInfo;
import com.example.demo.dcservice.IDcService;

@RestController
public class DcRestController {

	@Autowired
	private IDcService dcService;
	
	@GetMapping("/createCase/{appid}")
	public ResponseEntity<PlanSelection> createCase(@PathVariable Integer appid){
		PlanSelection createCase = dcService.createCase(appid);
		return new ResponseEntity<>(createCase,HttpStatus.OK);
	}
	
	@PostMapping("/applyplan")
	public ResponseEntity<Long> applyPlan(@RequestBody PlanSelection ps){
		Long updateCitizenPlan = dcService.updateCitizenPlan(ps);
		return new ResponseEntity<>(updateCitizenPlan,HttpStatus.OK);
	}
	
	@PostMapping("/saveIncome")
	public ResponseEntity<Long> saveIncome(@RequestBody Income income){
		Long saveIncomeInfo = dcService.saveIncomeInfo(income);
		return new ResponseEntity<>(saveIncomeInfo,HttpStatus.OK);
	}
	@PostMapping("/saveeducation")
	public ResponseEntity<Long> saveEducation(@RequestBody Education education){
		Long saveeducationinfo = dcService.saveEducationInfo(education);
		return new ResponseEntity<>(saveeducationinfo,HttpStatus.OK);
	}
	@PostMapping("/kidinfo")
	public ResponseEntity<Summary> saveKids(@RequestBody kidsInfo kidsinfo){
		 Summary saveKids = dcService.saveKids(kidsinfo);
		return new ResponseEntity<>(saveKids,HttpStatus.OK);
	}
}
