package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CO_TRIGGER")
@Data
public class CoTrigEntity {

	@Id
	@GeneratedValue
	private Integer coTrgId;
	private Long caseNumber;
	private byte[] pdf;
	private String trgStatus;
	
	
}
