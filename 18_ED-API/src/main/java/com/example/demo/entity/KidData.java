package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class KidData {
	@Id
	@GeneratedValue
	private Integer kidId;
	private long casenumber;
	private List<DcKidEntity> kiddetails;
}
