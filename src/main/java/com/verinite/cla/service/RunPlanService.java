package com.verinite.cla.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.verinite.cla.entity.RunPlan;


public interface RunPlanService {

	public RunPlan addRunPlan(RunPlan runPlan);
	
	public RunPlan updateRunPlan(RunPlan runPlan);
	
	public RunPlan findRunPlanById(String id);
	
	public List<RunPlan> findAllRunPlan();
	
	public List<RunPlan> findAllRunPlanByProjectId(String projectId);

	public String fetchRunPlan(String runPlanId) throws BadRequestException;
}
