package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.RunData;
import com.verinite.cla.service.RunDataService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rundata")
public class RunDataController {

	@Autowired
	private RunDataService runDataService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public RunData addNewRunData(@RequestBody RunData runData) {
		return runDataService.addRunData(runData);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<RunData> fetchAllRunDatas() {
		return runDataService.findAllRunData();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public RunData fetchRunDataById(@PathVariable ("id") String runDataId) {
		return runDataService.findRunDataById(runDataId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public RunData updateRunData(@RequestBody RunData runData) {
		return runDataService.updateRunData(runData);
	}
}
