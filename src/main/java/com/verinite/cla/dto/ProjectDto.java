package com.verinite.cla.dto;

import java.util.List;
import java.util.Map;

import com.verinite.cla.entity.Iteration;


public class ProjectDto {

	private String id;
	private String name;
	private String tenantId;
	private List<String> features;
	private Long startDate;
	private Boolean batchJob;
	private Boolean isFlowAuto;
	private List<Iteration> iterations;
	private Map<String, String> attributes;
		
	public ProjectDto(String name, String tenantId, List<String> features, Long startDate,
			List<Integer> validBillingCycles, String billingCycleSelectionCriteria, int dueDays
			, Map<String, String> attributes
			) {
		super();
		this.name = name;
		this.tenantId = tenantId;
		this.features = features;
		this.startDate = startDate;
//		this.validBillingCycles = validBillingCycles;
//		this.billingCycleSelectionCriteria = billingCycleSelectionCriteria;
//		this.dueDays = dueDays;
	}
	public ProjectDto() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getBatchJob() {
		return batchJob;
	}
	public void setBatchJob(Boolean batchJob) {
		this.batchJob = batchJob;
	}
	public Boolean getIsFlowAuto() {
		return isFlowAuto;
	}
	public void setIsFlowAuto(Boolean isFlowAuto) {
		this.isFlowAuto = isFlowAuto;
	}
	public List<Iteration> getIterations() {
		return iterations;
	}
	public void setIterations(List<Iteration> iterations) {
		this.iterations = iterations;
	}
}
