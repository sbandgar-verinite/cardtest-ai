package com.verinite.cla.entity;

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
	private Long runDate;
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
	private String batchRunStatus;
	private String preReportUrl;
	private String postReportUrl;
	private Long preRunTaskId;
	private Long postRunTaskId;
	private Long batchRunTaskId;
	private Long instanceId;
	private Boolean isPreExecEnable = Boolean.FALSE;
	private Boolean isPreUploadEnable = Boolean.FALSE;
	private Boolean isPostExecEnable = Boolean.FALSE;
	private Boolean isPostUploadEnable = Boolean.FALSE;
	private Integer itnSeq;

	public RunPlan(String id, int sequenceNumber, String projectId, String description, Long runDate,
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

	public Long getRunDate() {
		return runDate;
	}

	public void setRunDate(Long runDate) {
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

	public Long getPreRunTaskId() {
		return preRunTaskId;
	}

	public void setPreRunTaskId(Long preRunTaskId) {
		this.preRunTaskId = preRunTaskId;
	}

	public Long getPostRunTaskId() {
		return postRunTaskId;
	}

	public void setPostRunTaskId(Long postRunTaskId) {
		this.postRunTaskId = postRunTaskId;
	}

	public Long getBatchRunTaskId() {
		return batchRunTaskId;
	}

	public void setBatchRunTaskId(Long batchRunTaskId) {
		this.batchRunTaskId = batchRunTaskId;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public String getBatchRunStatus() {
		return batchRunStatus;
	}

	public void setBatchRunStatus(String batchRunStatus) {
		this.batchRunStatus = batchRunStatus;
	}

	public Boolean getIsPreExecEnable() {
		return isPreExecEnable;
	}

	public void setIsPreExecEnable(Boolean isPreExecEnable) {
		this.isPreExecEnable = isPreExecEnable;
	}

	public Boolean getIsPostExecEnable() {
		return isPostExecEnable;
	}

	public void setIsPostExecEnable(Boolean isPostExecEnable) {
		this.isPostExecEnable = isPostExecEnable;
	}

	public Boolean getIsPreUploadEnable() {
		return isPreUploadEnable;
	}

	public void setIsPreUploadEnable(Boolean isPreUploadEnable) {
		this.isPreUploadEnable = isPreUploadEnable;
	}

	public Boolean getIsPostUploadEnable() {
		return isPostUploadEnable;
	}

	public void setIsPostUploadEnable(Boolean isPostUploadEnable) {
		this.isPostUploadEnable = isPostUploadEnable;
	}

	public Integer getItnSeq() {
		return itnSeq;
	}

	public void setItnSeq(Integer itnSeq) {
		this.itnSeq = itnSeq;
	}
}
