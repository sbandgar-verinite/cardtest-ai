package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.exception.BadRequestException;

public interface RunPlanService {

	public RunPlan addRunPlan(RunPlan runPlan);

	public RunPlan updateRunPlan(RunPlan runPlan);

	public RunPlan findRunPlanById(String id);

	public List<RunPlan> findAllRunPlan();

	public List<RunPlan> findAllRunPlanByProjectId(String projectId);

	public String fetchRunPlan(String runPlanId, String scenarioType) throws BadRequestException, Exception;

	public void updateStatus(String runPlanId, String status, String url);
}
