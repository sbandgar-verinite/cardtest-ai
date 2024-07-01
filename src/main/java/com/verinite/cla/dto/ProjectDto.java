package com.verinite.cla.dto;

import java.util.List;
import java.util.Map;


public class ProjectDto {

	private String name;
	private String tenantId;
	private List<String> features;
	private String startDate;
	private List<Integer> validBillingCycles;
	private String billingCycleSelectionCriteria;
	private int dueDays;
	private Map<String, String> attributes;


	public ProjectDto(String name, String tenantId, List<String> features, String startDate,
			List<Integer> validBillingCycles, String billingCycleSelectionCriteria, int dueDays
			, Map<String, String> attributes
			) {
		super();
		this.name = name;
		this.tenantId = tenantId;
		this.features = features;
		this.startDate = startDate;
		this.validBillingCycles = validBillingCycles;
		this.billingCycleSelectionCriteria = billingCycleSelectionCriteria;
		this.dueDays = dueDays;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public List<Integer> getValidBillingCycles() {
		return validBillingCycles;
	}
	public void setValidBillingCycles(List<Integer> validBillingCycles) {
		this.validBillingCycles = validBillingCycles;
	}
	public String getBillingCycleSelectionCriteria() {
		return billingCycleSelectionCriteria;
	}
	public void setBillingCycleSelectionCriteria(String billingCycleSelectionCriteria) {
		this.billingCycleSelectionCriteria = billingCycleSelectionCriteria;
	}
	public int getDueDays() {
		return dueDays;
	}
	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	
}
