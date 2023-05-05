package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DC_EDUCATION")
@Data
public class Educationdetails {
	@Id
	@GeneratedValue
	private Integer educationId;
	private Long casenumber;
	private String universityname;
	private String highestdegree;
	private Integer graduateyear;
}
