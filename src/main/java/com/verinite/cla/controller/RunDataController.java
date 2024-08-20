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

import com.verinite.cla.entity.RunData;
import com.verinite.cla.service.RunDataService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rundata")
public class RunDataController {

	@Autowired
	private RunDataService runDataService;

	@PostMapping
	public RunData addNewRunData(@RequestBody RunData runData) {
		return runDataService.addRunData(runData);
	}

	@GetMapping
	public List<RunData> fetchAllRunDatas() {
		return runDataService.findAllRunData();
	}

	@GetMapping(value = "/{id}")
	public RunData fetchRunDataById(@PathVariable("id") String runDataId) {
		return runDataService.findRunDataById(runDataId);
	}

	@PutMapping
	public RunData updateRunData(@RequestBody RunData runData) {
		return runDataService.updateRunData(runData);
	}

	@PostMapping(value = "/project/{project_id}/generate")
	public void generateData(@PathVariable("project_id") String projectId)
			throws BadRequestException {
		runDataService.generateData(projectId);
	}
}
