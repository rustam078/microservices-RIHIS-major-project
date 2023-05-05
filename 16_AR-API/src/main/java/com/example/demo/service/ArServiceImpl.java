package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.binding.CitizenApp;
import com.example.demo.entity.CitizenAppEntity;
import com.example.demo.repository.Arrepository;

@Service
public class ArServiceImpl implements IArService {

	@Autowired
	private Arrepository arrepo;
	private static final String REST_URL = "http://ashvdjha.com";

	@Override
	public String registerArappln(CitizenApp app) {
		long ssn = app.getSsn();
		String statename = null;
		CitizenAppEntity entity1 = null;
		CitizenAppEntity findBySsn = arrepo.findBySsn(ssn);
		if (findBySsn == null) {
//			WebClient webClient = WebClient.create();
//			try {
//				statename = webClient.get().uri(REST_URL, ssn).retrieve().bodyToMono(String.class).block();
//			} catch (Exception e) {
//
//				throw new ArException(e.getMessage());
//			}
//			if ("Rodh Island".equals(statename)) {
//				CitizenAppEntity entity = new CitizenAppEntity();
//				BeanUtils.copyProperties(app, entity);
				var entity=CitizenAppEntity.builder()
						   .fname(app.getFname())
						   .email(app.getEmail())
						   .gender(app.getGender())
						   .mobileno(app.getMobileno())
						   .dob(app.getDob())
						   .ssn(app.getSsn())
						   .createdby(app.getCreatedby())
						   .updatedby(app.getUpdatedby())
						.build();
						
			    entity1 = arrepo.save(entity);
			
			} else {

				return "Citizen record not saved duplicate ssn";
			}
//		}
		return "Citizen record saved with id:: " + entity1.getAppId();
	}
}
