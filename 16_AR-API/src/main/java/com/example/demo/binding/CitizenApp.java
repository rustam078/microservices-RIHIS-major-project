package com.example.demo.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenApp {

	private String fname;
	private String email;
	private String gender;
	private long mobileno;
	private LocalDate dob;
	private long ssn;
	
	private Integer createdby;
	private Integer updatedby;
	
}
