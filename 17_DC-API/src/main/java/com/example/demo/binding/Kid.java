package com.example.demo.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Kid {
	private Integer kidId;
	private String kidname;
	private LocalDate kidDob;
	private String kidgender;
	private Long kidssn;
}
