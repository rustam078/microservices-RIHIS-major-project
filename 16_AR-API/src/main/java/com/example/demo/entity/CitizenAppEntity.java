package com.example.demo.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CITIZEN_APPS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppEntity {

	@Id
	@GeneratedValue
	private Integer appId;
	private String fname;
	private String email;
	private String gender;
	private Long mobileno;
	private LocalDate dob;
	private Long ssn;
	
	@CreationTimestamp
	private LocalDate creatDate;
	@UpdateTimestamp
	private LocalDate upDate;
	private Integer createdby;
	private Integer updatedby;
}
