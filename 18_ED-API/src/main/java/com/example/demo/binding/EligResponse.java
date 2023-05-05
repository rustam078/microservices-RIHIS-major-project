package com.example.demo.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligResponse {

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
