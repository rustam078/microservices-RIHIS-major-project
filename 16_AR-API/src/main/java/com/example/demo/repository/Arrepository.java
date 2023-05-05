package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CitizenAppEntity;

@Repository
public interface Arrepository extends JpaRepository<CitizenAppEntity, Serializable>{

	public CitizenAppEntity findBySsn(Long ssn);
}
