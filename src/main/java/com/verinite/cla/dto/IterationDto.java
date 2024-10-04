package com.verinite.cla.dto;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import java.util.List;


public class IterationDto {

	
	private Integer sequence;
	
	@Type(JsonType.class)
	private List<String> features;

	public IterationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IterationDto(Integer sequence, List<String> features) {
		super();
		this.sequence = sequence;
		this.features = features;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
	
	
}
