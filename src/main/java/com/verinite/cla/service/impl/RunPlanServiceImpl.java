package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.repository.RunPlanRepository;
import com.verinite.cla.service.RunPlanService;


@Service
public class RunPlanServiceImpl implements RunPlanService {

	@Autowired
	private RunPlanRepository runPlanRepository;
	
	@Override
	public RunPlan addRunPlan(RunPlan runPlan) {
		return runPlanRepository.save(runPlan);
	}

	@Override
	public RunPlan updateRunPlan(RunPlan runPlan) {
		return runPlanRepository.save(runPlan);
	}

	@Override
	public RunPlan findRunPlanById(String id) {
		return runPlanRepository.findById(id).orElse(null);
	}

	@Override
	public List<RunPlan> findAllRunPlan() {
		return runPlanRepository.findAll();
	}
	
	@Override
	public List<RunPlan> findAllRunPlanByProjectId(String projectId) {
		return runPlanRepository.getAllRunPlanByProjectId(projectId);
	}

}
