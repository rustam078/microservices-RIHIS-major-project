package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DcKidEntity;

public interface KidsRepo extends JpaRepository<DcKidEntity, Serializable>{

	public List<DcKidEntity> findByCasenumber(Long caseNum);
}
