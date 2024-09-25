package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.RunPlanDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.service.RunPlanService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/run-plans")
public class RunPlanController {

	@Autowired
	private RunPlanService runPlanService;

	@PostMapping
	public RunPlan addNewRunPlan(@RequestBody RunPlan runPlan) {
		return runPlanService.addRunPlan(runPlan);
	}

	@GetMapping
	public List<RunPlan> fetchAllRunPlans() {
		return runPlanService.findAllRunPlan();
	}

	@GetMapping(value = "/{id}")
	public RunPlan fetchRunPlanById(@PathVariable("id") String runPlanId) {
		return runPlanService.findRunPlanById(runPlanId);
	}

	@PutMapping
	public RunPlan updateRunPlan(@RequestBody RunPlan runPlan) {
		return runPlanService.updateRunPlan(runPlan);
	}

	@GetMapping(value = "/{id}/feature")
	public ResponseEntity<String> fetchRunPlan(@PathVariable("id") String runPlanId, @RequestParam String scenarioType)
			throws Exception {
		String response = runPlanService.fetchRunPlan(runPlanId, scenarioType);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/check-status/{id}")
	public StatusDto checkStatus(@PathVariable("id") String runPlanId) {
		return runPlanService.checkStatus(runPlanId);
	}

	@GetMapping(value = "/{id}/iteration/{iteration_id}")
	public List<RunPlanDto> fetchRunPlanByProjectIdIteration(@PathVariable("id") String projectId,
			@PathVariable("iteration_id") Integer iterationId) {
		return runPlanService.findRunPlanByProjectIdAndIterationId(projectId, iterationId);
	}
}
