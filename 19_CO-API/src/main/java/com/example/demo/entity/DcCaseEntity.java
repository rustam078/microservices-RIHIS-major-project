package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DC_CASES")
@Data
public class DcCaseEntity {
	@Id
	@GeneratedValue
	private Long caseNum;
	private Integer planId;
	private Integer appid;
}
