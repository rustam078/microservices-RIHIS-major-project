package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.IncomeDetails;

public interface IncomeRepo extends JpaRepository<IncomeDetails, Serializable>{

	public IncomeDetails findByCasenumber(Long casenumber);
}
