package com.example.demo.dcservice;

import com.example.demo.binding.Education;
import com.example.demo.binding.Income;
import com.example.demo.binding.PlanSelection;
import com.example.demo.binding.Summary;
import com.example.demo.binding.kidsInfo;

public interface IDcService {

	public PlanSelection createCase(Integer appId);
	public Long updateCitizenPlan(PlanSelection ps);
	public Long saveIncomeInfo(Income income);
	public Long saveEducationInfo(Education income);
	public Summary saveKids(kidsInfo kidinfo);
}
