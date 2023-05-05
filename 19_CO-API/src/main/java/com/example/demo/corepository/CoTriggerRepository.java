package com.example.demo.corepository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CoTrigEntity;

public interface CoTriggerRepository extends JpaRepository<CoTrigEntity, Serializable> {
	
	public List<CoTrigEntity> findByTrgStatus(String status);
	public CoTrigEntity findByCaseNumber(Long caseNum);
}
