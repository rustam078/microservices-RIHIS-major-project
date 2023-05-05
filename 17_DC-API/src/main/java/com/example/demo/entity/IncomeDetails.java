package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="DC_INCOME")
@Data
public class IncomeDetails {
	@Id
	@GeneratedValue
	private Integer incomeId;
	private Long casenumber;
	private Double salaryIncome;
	private Double rentIncome;
	private Double propertyIncome;

}
