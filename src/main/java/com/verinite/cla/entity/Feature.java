package com.verinite.cla.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.verinite.cla.model.RunConfig;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="feature")
public class Feature {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")
	private String id;
	
	private String description;
	private String code;
	private int numberOfRunsRequired;
	
//	@Type(JsonType.class)
//	private List<String> preRequisiteFeatures;
	
//	private String includeInFirstRun;
	
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(5000)")
	private List<RunConfig> runConfigs;
	
	public Feature(String id, String description, String code, int numberOfRunsRequired,
//			List<String> preRequisiteFeatures, 
//			String includeInFirstRun, 
			List<RunConfig> runConfigs) {
		super();
		this.id = id;
		this.description = description;
		this.code = code;
		this.numberOfRunsRequired = numberOfRunsRequired;
//		this.preRequisiteFeatures = preRequisiteFeatures;
//		this.includeInFirstRun = includeInFirstRun;
		this.runConfigs = runConfigs;
	}

	
	public Feature() {
		super();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getNumberOfRunsRequired() {
		return numberOfRunsRequired;
	}
	public void setNumberOfRunsRequired(int numberOfRunsRequired) {
		this.numberOfRunsRequired = numberOfRunsRequired;
	}
//	public List<String> getPreRequisiteFeatures() {
//		return preRequisiteFeatures;
//	}
//	public void setPreRequisiteFeatures(List<String> preRequisiteFeatures) {
//		this.preRequisiteFeatures = preRequisiteFeatures;
//	}
//	public String getIncludeInFirstRun() {
//		return includeInFirstRun;
//	}
//	public void setIncludeInFirstRun(String includeInFirstRun) {
//		this.includeInFirstRun = includeInFirstRun;
//	}
	public List<RunConfig> getRunConfigs() {
		return runConfigs;
	}
	public void setRunConfigs(List<RunConfig> runConfigs) {
		this.runConfigs = runConfigs;
	}
	
}
