package com.verinite.cla.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.verinite.cla.model.RunScenario;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "run-plan")
public class RunPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "VARCHAR(255)")
	private String id;
	private int sequenceNumber;
	private String projectId;
	private String description;
	private Date runDate;
	private Integer billingCycleConsidered;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(5000)")
	private List<RunScenario> preRunScripts;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(5000)")
	private List<RunScenario> postRunScripts;
	private String status;
	private String preRunStatus;
	private String postRunStatus;
	private String preReportUrl;
	private String postReportUrl;

	public RunPlan(String id, int sequenceNumber, String projectId, String description, Date runDate,
			Integer billingCycleConsidered, List<RunScenario> preRunScripts, List<RunScenario> postRunScripts,
			String status) {
		super();
		this.id = id;
		this.sequenceNumber = sequenceNumber;
		this.projectId = projectId;
		this.description = description;
		this.runDate = runDate;
		this.billingCycleConsidered = billingCycleConsidered;
		this.preRunScripts = preRunScripts;
		this.postRunScripts = postRunScripts;
		this.status = status;
	}

	public RunPlan() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RunScenario> getPreRunScripts() {
		return preRunScripts;
	}

	public void setPreRunScripts(List<RunScenario> preRunScripts) {
		this.preRunScripts = preRunScripts;
	}

	public List<RunScenario> getPostRunScripts() {
		return postRunScripts;
	}

	public void setPostRunScripts(List<RunScenario> postRunScripts) {
		this.postRunScripts = postRunScripts;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getBillingCycleConsidered() {
		return billingCycleConsidered;
	}

	public void setBillingCycleConsidered(Integer billingCycleConsidered) {
		this.billingCycleConsidered = billingCycleConsidered;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPreRunStatus() {
		return preRunStatus;
	}

	public void setPreRunStatus(String preRunStatus) {
		this.preRunStatus = preRunStatus;
	}

	public String getPostRunStatus() {
		return postRunStatus;
	}

	public void setPostRunStatus(String postRunStatus) {
		this.postRunStatus = postRunStatus;
	}

	public String getPreReportUrl() {
		return preReportUrl;
	}

	public void setPreReportUrl(String preReportUrl) {
		this.preReportUrl = preReportUrl;
	}

	public String getPostReportUrl() {
		return postReportUrl;
	}

	public void setPostReportUrl(String postReportUrl) {
		this.postReportUrl = postReportUrl;
	}
}
