package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CitizenAppEntity;

public interface Arrepository extends JpaRepository<CitizenAppEntity, Serializable>{

}
