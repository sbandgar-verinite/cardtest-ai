package com.verinite.cla.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="scenario")
public class Scenario {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")
	private String id;
	private String code;
	private String description;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(500)")
	private List<String> givenStatements;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(500)")
	private List<String> whenConditions;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(500)")
	private List<String> thenOutcomes;
	@Type(JsonType.class)
	@Column(columnDefinition = "VARBINARY(500)")
	private List<String> entitiesRequired;
	
	
	public Scenario(String id, String code, String description, List<String> givenStatements,
			List<String> whenConditions, List<String> thenOutcomes, List<String> entitiesRequired) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.givenStatements = givenStatements;
		this.whenConditions = whenConditions;
		this.thenOutcomes = thenOutcomes;
		this.entitiesRequired = entitiesRequired;
	}


	public Scenario() {
		super();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getGivenStatements() {
		return givenStatements;
	}
	public void setGivenStatements(List<String> givenStatements) {
		this.givenStatements = givenStatements;
	}
	public List<String> getWhenConditions() {
		return whenConditions;
	}
	public void setWhenConditions(List<String> whenConditions) {
		this.whenConditions = whenConditions;
	}
	public List<String> getThenOutcomes() {
		return thenOutcomes;
	}
	public void setThenOutcomes(List<String> thenOutcomes) {
		this.thenOutcomes = thenOutcomes;
	}


	public List<String> getEntitiesRequired() {
		return entitiesRequired;
	}


	public void setEntitiesRequired(List<String> entitiesRequired) {
		this.entitiesRequired = entitiesRequired;
	}
	
}
