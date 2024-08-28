package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.dto.RunPlanDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.entity.RunPlan;
import com.verinite.commons.controlleradvice.BadRequestException;

public interface RunPlanService {

	public RunPlan addRunPlan(RunPlan runPlan);

	public RunPlan updateRunPlan(RunPlan runPlan);

	public RunPlan findRunPlanById(String id);

	public List<RunPlan> findAllRunPlan();

	public List<RunPlanDto> findAllRunPlanByProjectId(String projectId);

	public String fetchRunPlan(String runPlanId, String scenarioType) throws BadRequestException, Exception;

	public void updateStatus(String runPlanId, String status, String url, String type);

	public StatusDto checkStatus(String runPlanId);
}
