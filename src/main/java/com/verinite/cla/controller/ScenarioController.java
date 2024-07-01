package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Scenario;
import com.verinite.cla.service.ScenarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/scenarios")
public class ScenarioController {

	@Autowired
	private ScenarioService scenarioService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Scenario addNewScenario(@RequestBody Scenario scenario) {
		return scenarioService.addScenario(scenario);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Scenario> fetchAllScenarios() {
		return scenarioService.findAllScenario();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public Scenario fetchScenarioById(@PathVariable ("id") String scenarioId) {
		return scenarioService.findScenarioById(scenarioId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public Scenario updateScenario(@RequestBody Scenario scenario) {
		return scenarioService.updateScenario(scenario);
	}
}
