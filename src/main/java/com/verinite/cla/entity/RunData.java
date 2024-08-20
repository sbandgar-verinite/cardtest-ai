package com.verinite.cla.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="rundata")
public class RunData {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")
	private String id;
	private String code;
	private String entityName;
	@Type(JsonStringType.class)
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public RunData(String id, String code, String entityName, Map<String, String> attributes) {
		super();
		this.id = id;
		this.code = code;
		this.entityName = entityName;
		this.attributes = attributes;
	}

	public RunData() {
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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
