package com.verinite.cla.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.verinite.cla.model.Attribute;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dataentity")
public class EntityDefinition {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="id", columnDefinition="VARCHAR(255)")
	private String id;
	private String name;
	private String code;
	private String childOf;
	
	@Type(JsonType.class)
	private List<Attribute> attributes;

	public EntityDefinition(String id, String name, String code, String childOf, List<Attribute> attributes) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.childOf = childOf;
		this.attributes = attributes;
	}

	public EntityDefinition() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getChildOf() {
		return childOf;
	}

	public void setChildOf(String childOf) {
		this.childOf = childOf;
	}
	
}
