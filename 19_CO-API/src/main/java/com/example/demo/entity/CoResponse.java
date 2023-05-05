package com.example.demo.entity;

import lombok.Data;

@Data
public class CoResponse {
	private Long totalTriggers;
	private Long succTriggers;
	private Long failedTrigger;
}
