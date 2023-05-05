package com.example.demo.dcservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.binding.Education;
import com.example.demo.binding.Income;
import com.example.demo.binding.Kid;
import com.example.demo.binding.PlanSelection;
import com.example.demo.binding.Summary;
import com.example.demo.binding.kidsInfo;
import com.example.demo.entity.CitizenAppEntity;
import com.example.demo.entity.DcCaseEntity;
import com.example.demo.entity.DcKidEntity;
import com.example.demo.entity.Educationdetails;
import com.example.demo.entity.IncomeDetails;
import com.example.demo.entity.PlanEntity;
import com.example.demo.repository.Arrepository;
import com.example.demo.repository.EducationRepo;
import com.example.demo.repository.IncomeRepo;
import com.example.demo.repository.KidsRepo;
import com.example.demo.repository.PlanEntityRepo;
import com.example.demo.repository.caseRepo;

@Service
public class DCServiceImpl implements IDcService {

	private Arrepository arrepo;
	private EducationRepo edurepo;
	private IncomeRepo incomerepo;
	private KidsRepo kidrepo;
	private PlanEntityRepo planentityrepo;
	private caseRepo caserepo;

	public DCServiceImpl(Arrepository arrepo, EducationRepo edurepo, IncomeRepo incomerepo, KidsRepo kidrepo,
			PlanEntityRepo planentityrepo, caseRepo caserepo) {
		super();
		this.arrepo = arrepo;
		this.edurepo = edurepo;
		this.incomerepo = incomerepo;
		this.kidrepo = kidrepo;
		this.planentityrepo = planentityrepo;
		this.caserepo = caserepo;
	}

	@Override
	public PlanSelection createCase(Integer appId) {
		PlanSelection plansec = new PlanSelection();
		Optional<CitizenAppEntity> appid = arrepo.findById(appId);
		if (appid.isPresent()) {
			 Optional<DcCaseEntity> findByAppid = caserepo.findByAppid(appId);
			Map<Integer, String> plan = new HashMap<>();
			List<PlanEntity> findAll = planentityrepo.findAll();
			findAll.forEach(plann -> {
				plan.put(plann.getPlanId(), plann.getPlanName());
			});

			if(findByAppid.isEmpty()) {
			DcCaseEntity caseEntity = new DcCaseEntity();
			caseEntity.setAppid(appId);
			caseEntity = caserepo.save(caseEntity);

		
			plansec.setPlaninfo(plan);
			plansec.setCasenumber(caseEntity.getCaseNum());
			}else {
				plansec.setCasenumber(findByAppid.get().getCaseNum());
				plansec.setPlanId(findByAppid.get().getPlanId());
				plansec.setPlaninfo(plan);
			}
		}
		return plansec;
	}

	@Override
	public Long updateCitizenPlan(PlanSelection ps) {
		Long casenumber = ps.getCasenumber();
		Integer planId = ps.getPlanId();
		Optional<DcCaseEntity> findById = caserepo.findById(casenumber);
		if (findById.isPresent()) {
			DcCaseEntity dcCaseEntity = findById.get();
			dcCaseEntity.setPlanId(planId);
			caserepo.save(dcCaseEntity);
		}
		return casenumber;
	}

	@Override
	public Long saveIncomeInfo(Income income) {
		IncomeDetails incomeDetails = new IncomeDetails();
		BeanUtils.copyProperties(income, incomeDetails);
		IncomeDetails findByCasenumber = incomerepo.findByCasenumber(income.getCasenumber());
		Optional<DcCaseEntity> findById = caserepo.findById(income.getCasenumber());
		if(findById.isPresent()) {
		if(findByCasenumber==null) {
		incomerepo.save(incomeDetails);
		}else {
			incomeDetails.setIncomeId(findByCasenumber.getIncomeId());
			incomerepo.save(incomeDetails);
		}
		return income.getCasenumber();
		}else {
			return 0l;
		}
	}

	@Override
	public Long saveEducationInfo(Education income) {
		Educationdetails educationdetails = new Educationdetails();
		BeanUtils.copyProperties(income, educationdetails);
		Educationdetails findByCasenumber = edurepo.findByCasenumber(income.getCasenumber());
		Optional<DcCaseEntity> findById = caserepo.findById(income.getCasenumber());
		if(findById.isPresent()) {
		if(findByCasenumber==null) {
			edurepo.save(educationdetails);
			}else {
			educationdetails.setEducationId(findByCasenumber.getEducationId());
			edurepo.save(educationdetails);		}
		return income.getCasenumber();
		}else {
			return 0l;
		}

	}

	@Override
	public Summary saveKids(kidsInfo kiddata) {
		Long casenumber = kiddata.getCasenumber();
		Optional<DcCaseEntity> findById = caserepo.findById(casenumber);
		if(findById.isPresent()) {
		List<Kid> kids = kiddata.getKids();
		List<DcKidEntity> kidentities = new ArrayList<>();
		kids.forEach(kid -> {
			DcKidEntity kidsDetails = new DcKidEntity();
			BeanUtils.copyProperties(kid, kidsDetails);
			kidsDetails.setCasenumber(casenumber);
			kidentities.add(kidsDetails);
		});
		kidrepo.saveAll(kidentities);
		return getSummary(casenumber);
		}else {
			return null;
		}
	}

	private Summary getSummary(Long caseNum) {
		Optional<DcCaseEntity> dccase = caserepo.findById(caseNum);
		Integer appid = dccase.get().getAppid();
		Integer planId = dccase.get().getPlanId();
		Optional<PlanEntity> plan = planentityrepo.findById(planId);
		String planName = plan.get().getPlanName();
		Optional<CitizenAppEntity> app = arrepo.findById(appid);
		System.err.println(app.get());
		String fname = app.get().getFname();
		Long ssn = app.get().getSsn();
		IncomeDetails incomeDetails = incomerepo.findByCasenumber(caseNum);
		Educationdetails educationdetails = edurepo.findByCasenumber(caseNum);
		List<DcKidEntity> kidEntities = kidrepo.findByCasenumber(caseNum);
		
		Summary summary = new Summary();
		summary.setFname(fname);
		summary.setSsn(ssn);
		summary.setPlanName(planName);
		Income income = new Income();
		BeanUtils.copyProperties(incomeDetails, income);
		Education education = new Education();
		BeanUtils.copyProperties(educationdetails, education);
		summary.setEducationinfo(education);
		kidsInfo kidsInfo = new kidsInfo();
		kidsInfo.setCasenumber(caseNum);
		List<Kid> kidd=new ArrayList<>();
		kidEntities.forEach(kid->{
			Kid kid2 = new Kid();
			BeanUtils.copyProperties(kid, kid2);
			kidd.add(kid2);
			
		});
		kidsInfo.setKids(kidd);
		summary.setKidinfo(kidsInfo);
		summary.setIncomeinfo(income);
		// set icome edu kid details
		return summary;
	}
}
