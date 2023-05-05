package com.example.demo.binding;

import lombok.Data;

@Data
public class Summary {
	private String fname;
	private Long ssn;
	private String planName;
	private Income incomeinfo;
	private Education educationinfo;
	private kidsInfo kidinfo;
}
