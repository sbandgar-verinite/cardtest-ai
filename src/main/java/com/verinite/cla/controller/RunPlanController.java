package com.verinite.cla.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String fetchRunPlan(@PathVariable("id") String runPlanId) throws BadRequestException {
		return runPlanService.fetchRunPlan(runPlanId);
	}
}
