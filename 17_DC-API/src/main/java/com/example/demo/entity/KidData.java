package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class KidData {

	private long casenumber;
	private List<DcKidEntity> kiddetails;
}
