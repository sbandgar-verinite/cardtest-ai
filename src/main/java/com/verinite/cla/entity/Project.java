package com.verinite.cla.entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="project")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")
	private String id;
	
	private String name;
	private String tenantId;
	
	@Type(JsonType.class)
	private List<String> features;
	
	private Date startDate;
	
//	@Type(JsonType.class)
//	private List<Integer> validBillingCycles;
	
//	private String billingCycleSelectionCriteria; //NEAREST from startDate, FARTHEST from startDate, FARTHEST WITHIN 15DAYS from startDate

//	private int dueDays;
	
	@ElementCollection
	private Map<String, String> attributes = new HashMap<>();

	public Project(String id, String name, String tenantId, List<String> features, Date startDate,
			List<Integer> validBillingCycles, String billingCycleSelectionCriteria, int dueDays,
			Map<String, String> attributes) {
		super();
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.features = features;
		this.startDate = startDate;
//		this.validBillingCycles = validBillingCycles;
//		this.billingCycleSelectionCriteria = billingCycleSelectionCriteria;
//		this.dueDays = dueDays;
		this.attributes = attributes;
	}

	public Project() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

//	public List<Integer> getValidBillingCycles() {
//		return validBillingCycles;
//	}
//
//	public void setValidBillingCycles(List<Integer> validBillingCycles) {
//		this.validBillingCycles = validBillingCycles;
//	}
//
//	public String getBillingCycleSelectionCriteria() {
//		return billingCycleSelectionCriteria;
//	}
//
//	public void setBillingCycleSelectionCriteria(String billingCycleSelectionCriteria) {
//		this.billingCycleSelectionCriteria = billingCycleSelectionCriteria;
//	}
//
//	public int getDueDays() {
//		return dueDays;
//	}
//
//	public void setDueDays(int dueDays) {
//		this.dueDays = dueDays;
//	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	
	
}
