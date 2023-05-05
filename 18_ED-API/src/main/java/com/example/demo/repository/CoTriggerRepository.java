package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CoTrigEntity;

public interface CoTriggerRepository extends JpaRepository<CoTrigEntity, Serializable> {

}
