package com.verinite.cla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "defect")
public class Defect {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "VARCHAR(255)")
	private String id;

	private String defectId;

	private String caseId;

	private String caseModule;

	private String severity;

	private String details;

	public Defect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Defect(String id, String defectId, String caseId, String caseModule, String severity, String details) {
		super();
		this.id = id;
		this.defectId = defectId;
		this.caseId = caseId;
		this.caseModule = caseModule;
		this.severity = severity;
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefectId() {
		return defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseModule() {
		return caseModule;
	}

	public void setCaseModule(String caseModule) {
		this.caseModule = caseModule;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
