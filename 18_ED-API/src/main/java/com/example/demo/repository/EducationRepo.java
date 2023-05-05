package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Educationdetails;

public interface EducationRepo extends JpaRepository<Educationdetails, Serializable>{

	public Educationdetails findByCasenumber(Long casenumber);
}
