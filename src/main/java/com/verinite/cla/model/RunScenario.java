package com.verinite.cla.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;

public class RunScenario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4520329268367039972L;

	private String featureCode;
	@Type(JsonType.class)
	private List<String> scenarios;
	
	
	public RunScenario(String featureCode, List<String> scenarios) {
		super();
		this.featureCode = featureCode;
		this.scenarios = scenarios;
	}
	public RunScenario() {
		super();
	}
	public String getFeatureCode() {
		return featureCode;
	}
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}
	public List<String> getScenarios() {
		return scenarios;
	}
	public void setScenarios(List<String> scenarios) {
		this.scenarios = scenarios;
	}	
	
}
