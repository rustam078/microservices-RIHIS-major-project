package com.example.demo.corepository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EDEligDtlsEntity;

public interface EdEligRepository extends JpaRepository<EDEligDtlsEntity, Serializable> {

	public EDEligDtlsEntity findByCasenumber(Long casenum);
}
