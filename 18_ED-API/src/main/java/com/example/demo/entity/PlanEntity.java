package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PLAN_MASTER")
@Data
public class PlanEntity {
	@Id
	@GeneratedValue
	private Integer planId;
	private String planName;

}
