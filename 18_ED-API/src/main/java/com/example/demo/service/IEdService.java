package com.example.demo.service;

import com.example.demo.binding.EligResponse;

public interface IEdService {

	public EligResponse determineEligibility(Long casenum);
}
