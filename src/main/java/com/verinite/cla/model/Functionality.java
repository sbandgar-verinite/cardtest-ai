package com.verinite.cla.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "case_details")
public class Functionality {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String caseId;

	private String funcName;

	private String subFuncName;

	private List<String> tags;

	private String featureId;

	private String featureName;

	private String caseDescription;

	private String casePrecondition;

	private String testData;

	private String preRequisite;

	private List<String> steps;

	private String priority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getSubFuncName() {
		return subFuncName;
	}

	public void setSubFuncName(String subFuncName) {
		this.subFuncName = subFuncName;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCasePrecondition() {
		return casePrecondition;
	}

	public void setCasePrecondition(String casePrecondition) {
		this.casePrecondition = casePrecondition;
	}

	public String getTestData() {
		return testData;
	}

	public void setTestData(String testData) {
		this.testData = testData;
	}

	public String getPreRequisite() {
		return preRequisite;
	}

	public void setPreRequisite(String preRequisite) {
		this.preRequisite = preRequisite;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> list) {
		this.steps = list;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
