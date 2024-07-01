package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.service.RunPlanService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/run-plans")
public class RunPlanController {

	@Autowired
	private RunPlanService runPlanService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public RunPlan addNewRunPlan(@RequestBody RunPlan runPlan) {
		return runPlanService.addRunPlan(runPlan);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<RunPlan> fetchAllRunPlans() {
		return runPlanService.findAllRunPlan();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public RunPlan fetchRunPlanById(@PathVariable ("id") String runPlanId) {
		return runPlanService.findRunPlanById(runPlanId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public RunPlan updateRunPlan(@RequestBody RunPlan runPlan) {
		return runPlanService.updateRunPlan(runPlan);
	}
}
