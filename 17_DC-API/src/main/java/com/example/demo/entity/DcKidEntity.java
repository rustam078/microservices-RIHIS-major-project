package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="DC_KIDS")
@Data
public class DcKidEntity {
	@Id
	@GeneratedValue
	private Integer kidId;
	private String kidname;
	private LocalDate kidDob;
	private String kidgender;
	private Long kidssn;
	private Long casenumber;
}
