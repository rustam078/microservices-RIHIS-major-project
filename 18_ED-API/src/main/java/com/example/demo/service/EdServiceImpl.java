package com.example.demo.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.binding.EligResponse;
import com.example.demo.entity.CitizenAppEntity;
import com.example.demo.entity.CoTrigEntity;
import com.example.demo.entity.DcCaseEntity;
import com.example.demo.entity.DcKidEntity;
import com.example.demo.entity.EDEligDtlsEntity;
import com.example.demo.entity.Educationdetails;
import com.example.demo.entity.IncomeDetails;
import com.example.demo.entity.PlanEntity;
import com.example.demo.repository.Arrepository;
import com.example.demo.repository.CoTriggerRepository;
import com.example.demo.repository.EdEligRepository;
import com.example.demo.repository.EducationRepo;
import com.example.demo.repository.IncomeRepo;
import com.example.demo.repository.KidsRepo;
import com.example.demo.repository.PlanEntityRepo;
import com.example.demo.repository.caseRepo;

@Service
public class EdServiceImpl implements IEdService {

	private Arrepository arrepo;
	private EducationRepo edurepo;
	private IncomeRepo incomerepo;
	private KidsRepo kidrepo;
	private PlanEntityRepo planentityrepo;
	private caseRepo caserepo;
	private EdEligRepository edeligrepo;
	private CoTriggerRepository cotrigrepo;
	
	boolean nokids=true;
	boolean eligible=true;
	public EdServiceImpl(Arrepository arrepo, EducationRepo edurepo, IncomeRepo incomerepo, KidsRepo kidrepo,
			PlanEntityRepo planentityrepo, caseRepo caserepo, EdEligRepository edeligrepo,
			CoTriggerRepository cotrigrepo) {
		super();
		this.arrepo = arrepo;
		this.edurepo = edurepo;
		this.incomerepo = incomerepo;
		this.kidrepo = kidrepo;
		this.planentityrepo = planentityrepo;
		this.caserepo = caserepo;
		this.edeligrepo = edeligrepo;
		this.cotrigrepo = cotrigrepo;
	}

	@Override
	public EligResponse determineEligibility(Long casenum) {
		Integer appid ;
		Integer planId;
		 String planName =null;
		 LocalDate citizendob=null;
		 String fname=null;
			Optional<CitizenAppEntity> citizendtls =null;
		Optional<DcCaseEntity> findById = caserepo.findById(casenum);
		Educationdetails educationdetails = edurepo.findByCasenumber(casenum);
		IncomeDetails incomeDetails = incomerepo.findByCasenumber(casenum);
		List<DcKidEntity> kids = kidrepo.findByCasenumber(casenum);
		EligResponse response = new EligResponse();
		if(findById.isPresent()) {
			 appid = findById.get().getAppid();
			 planId = findById.get().getPlanId();
				citizendtls = arrepo.findById(appid);
				if(citizendtls.isPresent()) {
				 citizendob = citizendtls.get().getDob();
				  fname = citizendtls.get().getFname();
				         
				}
			 Optional<PlanEntity> planname = planentityrepo.findById(planId);
			 if(planname.isPresent()) {
				 planName = planname.get().getPlanName();
				 
				 if("SNAP".equals(planName)) {
					 
					 if(incomeDetails.getSalaryIncome()>300) {
						 response.setDenialReason("Income is high");
					 }
				 }
				 else if("CCAP".equals(planName)) {
					 if(!kids.isEmpty()) {
						 kids.forEach(kid->{
							LocalDate dob= kid.getKidDob();
							LocalDate date = LocalDate.now();
							Period between = Period.between(dob, date);
							int years = between.getYears();
							if(years>16) {
								eligible=false;
							}
						 });
					 }else {
						 nokids=false;
						 response.setDenialReason("No kids");
					 }
					 if(incomeDetails.getSalaryIncome()>300) {
						 response.setDenialReason("Salary income is high");
					 }if(!eligible) {
						 response.setDenialReason("kid age is high");
					 }
					 if(incomeDetails.getSalaryIncome()>300&&!eligible) {
						 response.setDenialReason("high income +kid age high");
					 }
					 
				 }else if("Medicaid".equals(planName)) {
					 if(incomeDetails.getSalaryIncome()>300) {
						 response.setDenialReason("Salary income is high");
					 }
					 if(incomeDetails.getPropertyIncome()>0) {
						 response.setDenialReason("property income is high");
					 }
					 if(incomeDetails.getRentIncome()>0) {
						 response.setDenialReason("rent income is high");
					 }
					 if(incomeDetails.getRentIncome()>0&& incomeDetails.getPropertyIncome()>0) {
						 response.setDenialReason("rent +property income is high");
					 }
				 }else if("Medicare".equals(planName)) {
					 LocalDate now = LocalDate.now();
					 Period between = Period.between(citizendob, now);
					 int years = between.getYears();
					 if(years>65) {
						 response.setDenialReason("age is to high");
					 }
				 }else {
					 if(educationdetails.getGraduateyear()<0) {
						 response.setDenialReason("not graduated");
					 }if(educationdetails.getGraduateyear()>0&&incomeDetails.getSalaryIncome()>0) {
						 response.setDenialReason("high income");
					 }
					 
				 }
			 }
		}
		response.setCasenumber(casenum);
		response.setHolderName(fname);
		response.setHolderSsn(citizendtls.get().getSsn());
		response.setPlanName(planName);
		if(response.getDenialReason()!=null) {
			response.setPlanStatus("denied");
		}else {
		
			response.setPlanStartDate(LocalDate.now().plusDays(1));
			response.setPlanEndDate(LocalDate.now().plusMonths(3));
			response.setBenifitAmount(350.00);
			response.setPlanStatus("accepted");
		
		
		}
		EDEligDtlsEntity eligDtlsEntity = new EDEligDtlsEntity();
		BeanUtils.copyProperties(response, eligDtlsEntity);
		edeligrepo.save(eligDtlsEntity);
		
		CoTrigEntity coTrigEntity = new CoTrigEntity();
		coTrigEntity.setCaseNumber(casenum);
		coTrigEntity.setTrgStatus("pending");
		cotrigrepo.save(coTrigEntity);
		return response;
	}

}
