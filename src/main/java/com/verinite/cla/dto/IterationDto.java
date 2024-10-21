package com.verinite.cla.dto;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.micrometer.common.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import java.util.List;


public class IterationDto {
	

	@JsonIgnore
	private String id;
	
	private Integer sequence;

	@Type(JsonType.class)
	private List<String> features;

	private List<RunPlanDto> runPlanDtoList ;

	private Long startDate;
	
	public IterationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IterationDto(Integer sequence, List<String> features) {
		super();
		this.sequence = sequence;
		this.features = features;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<RunPlanDto> getRunPlanDtoList() {
		return runPlanDtoList;
	}

	public void setRunPlanDtoList(List<RunPlanDto> runPlanDtoList) {
		this.runPlanDtoList = runPlanDtoList;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

}
