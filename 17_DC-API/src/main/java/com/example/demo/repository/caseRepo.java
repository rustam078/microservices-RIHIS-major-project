package com.example.demo.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DcCaseEntity;

public interface caseRepo extends JpaRepository<DcCaseEntity, Serializable>{

	public Optional<DcCaseEntity> findByAppid(Integer appid);
}
