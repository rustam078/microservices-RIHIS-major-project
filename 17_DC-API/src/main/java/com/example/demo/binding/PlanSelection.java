package com.example.demo.binding;

import java.util.Map;

import lombok.Data;

@Data
public class PlanSelection {
private Long casenumber;
private Integer planId;
private Map<Integer,String> planinfo;
}
