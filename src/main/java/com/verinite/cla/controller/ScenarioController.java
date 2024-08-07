package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Scenario;
import com.verinite.cla.service.ScenarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/scenarios")
public class ScenarioController {

	@Autowired
	private ScenarioService scenarioService;

	@PostMapping
	public Scenario addNewScenario(@RequestBody Scenario scenario) {
		return scenarioService.addScenario(scenario);
	}

	@GetMapping
	public List<Scenario> fetchAllScenarios() {
		return scenarioService.findAllScenario();
	}

	@GetMapping(value = "/{id}")
	public Scenario fetchScenarioById(@PathVariable("id") String scenarioId) {
		return scenarioService.findScenarioById(scenarioId);
	}

	@PutMapping
	public Scenario updateScenario(@RequestBody Scenario scenario) {
		return scenarioService.updateScenario(scenario);
	}
}
