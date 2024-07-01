package com.verinite.cla.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;

public class RunConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3366235077360385196L;
	private Long runNumber;
	private String description;
	private String runType;
	@Type(JsonType.class)
	private List<String> preRunScripts;
	@Type(JsonType.class)
	private List<String> postRunScripts;

	public RunConfig(Long runNumber, String description, String runType, List<String> preRunScripts,
			List<String> postRunScripts) {
		super();
		this.runNumber = runNumber;
		this.description = description;
		this.runType = runType;
		this.preRunScripts = preRunScripts;
		this.postRunScripts = postRunScripts;
	}
	public RunConfig() {
		super();
	}
	public Long getRunNumber() {
		return runNumber;
	}
	public void setRunNumber(Long runNumber) {
		this.runNumber = runNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getPreRunScripts() {
		return preRunScripts;
	}
	public void setPreRunScripts(List<String> preRunScripts) {
		this.preRunScripts = preRunScripts;
	}
	public List<String> getPostRunScripts() {
		return postRunScripts;
	}
	public void setPostRunScripts(List<String> postRunScripts) {
		this.postRunScripts = postRunScripts;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	
}
