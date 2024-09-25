package com.verinite.cla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="defect")

public class Defect {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")

	private String defectId;
	private String caseId;
	private String severity;
	private String details;
	private String caseModule;

	public Defect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Defect(String defectId, String caseId, String severity, String details, String caseModule) {
		super();
		this.defectId = defectId;
		this.caseId = caseId;
		this.severity = severity;
		this.details = details;
		this.caseModule = caseModule;
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



	public String getCaseModule() {
		return caseModule;
	}



	public void setCaseModule(String caseModule) {
		this.caseModule = caseModule;
	}
	
	
	
	
	
	
}
