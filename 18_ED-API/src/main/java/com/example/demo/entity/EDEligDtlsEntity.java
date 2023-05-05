package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ELIG_DTLS")
@Data
public class EDEligDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer edTraceId;
	private Long casenumber;
	private String holderName;
	private Long holderSsn;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benifitAmount;
	private String denialReason; 
	

}
